package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.googlePay.GooglePayHelper
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.googlePay.GooglePayActivityContract
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleGooglePay
import com.paymentpage.msdk.ui.presentation.main.showError
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.views.button.GooglePayButton
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun GooglePayItem(method: UIPaymentMethod.UIGooglePayPaymentMethod) {
    val paymentOptions = LocalPaymentOptions.current
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val visibleCustomerFields = remember { customerFields.visibleCustomerFields() }
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    val isForcePaymentMethod =
        paymentOptions.paymentInfo.forcePaymentMethod == PaymentMethodType.GOOGLE_PAY.value

    val merchantId = LocalPaymentOptions.current.merchantId
    val merchantName = LocalPaymentOptions.current.merchantName
    var isGooglePayAvailable by remember { mutableStateOf(isForcePaymentMethod) }
    var isGooglePayOpened by remember { mutableStateOf(false) }
    val googlePayHelper = GooglePayHelper(merchantId, merchantName)
    val activity = LocalContext.current as PaymentActivity
    val googlePayClient = remember {
        Wallet.getPaymentsClient(
            activity,
            Wallet.WalletOptions.Builder()
                .setEnvironment(if (paymentOptions.merchantEnvironment == GooglePayEnvironment.TEST) WalletConstants.ENVIRONMENT_TEST else WalletConstants.ENVIRONMENT_PRODUCTION)//test
                //.setTheme(WalletConstants.THEME_LIGHT)
                .build()
        )
    }
    val handle: (GooglePayActivityContract.Result) -> Unit = { result ->
        if (!result.errorMessage.isNullOrEmpty()) {
            viewModel.showError(ErrorResult(code = ErrorCode.UNKNOWN, message = result.errorMessage))
        } else
            result.token?.let {
                viewModel.saleGooglePay(
                    method = method,
                    merchantId = merchantId,
                    token = it,
                    environment = paymentOptions.merchantEnvironment,
                )
            }
    }
    val launcher = rememberLauncherForActivityResult(GooglePayActivityContract()) { result ->
        isGooglePayOpened = false
        handle(result)
    }
    val launchGooglePaySheet = {
        isGooglePayOpened = true
        launcher.launch(
            GooglePayActivityContract.Config(
                merchantId = merchantId,
                merchantName = merchantName,
                merchantEnvironment = paymentOptions.merchantEnvironment,
                amount = paymentOptions.paymentInfo.paymentAmount,
                currency = paymentOptions.paymentInfo.paymentCurrency
            )
        )
    }

    if (!isForcePaymentMethod)
        LaunchedEffect(Unit) {
            val request =
                IsReadyToPayRequest.fromJson(googlePayHelper.createReadyToPayRequest().toString())
            googlePayClient.isReadyToPay(request).addOnCompleteListener { completedTask ->
                isGooglePayAvailable = completedTask.isSuccessful
            }
        }

    when {
        customerFields.hasVisibleCustomerFields() -> {
            ExpandablePaymentMethodItem(
                method = method,
                headerBackgroundColor = SDKTheme.colors.backgroundColor,
                fallbackIcon = painterResource(id = SDKTheme.images.googlePayMethodResId),
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                CustomerFields(
                    customerFields = visibleCustomerFields,
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues,
                    onCustomerFieldsChanged = { fields, isValid ->
                        method.customerFieldValues = fields
                        isCustomerFieldsValid = isValid
                        method.isCustomerFieldsValid = isValid
                    }
                )
                Spacer(modifier = Modifier.size(22.dp))
                GooglePayButton(
                    isEnabled = isCustomerFieldsValid && isGooglePayAvailable && !isGooglePayOpened,
                    onClick = launchGooglePaySheet
                )
            }
        }
        customerFields.isAllCustomerFieldsHidden() && isForcePaymentMethod -> {
            LaunchedEffect(key1 = Unit, block = { launchGooglePaySheet() })
            GooglePayButton(
                isEnabled = isGooglePayAvailable && !isGooglePayOpened,
                onClick = launchGooglePaySheet
            )
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            GooglePayButton(
                isEnabled = isGooglePayAvailable && !isGooglePayOpened,
                onClick = launchGooglePaySheet
            )
        }
    }
}