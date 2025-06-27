package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.googlePay.GooglePayHelper
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.googlePay.GooglePayActivityContract
import com.paymentpage.msdk.ui.presentation.main.payGoogle
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.showError
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.mergeHiddenFieldsToList
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.recurring.RecurringAgreements
import com.paymentpage.msdk.ui.views.button.GooglePayButton

@Composable
internal fun GooglePayItem(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    val lastState = LocalMainViewModel.current.lastState
    val paymentOptions = LocalPaymentOptions.current
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = paymentOptions.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    val isForcePaymentMethod =
        paymentOptions.paymentInfo.forcePaymentMethod == PaymentMethodType.GOOGLE_PAY.value
    val isTryAgain = lastState.isTryAgain ?: false

    val merchantId = paymentOptions.merchantId
    val merchantName = paymentOptions.merchantName
    var isGooglePayAvailable by remember { mutableStateOf(isForcePaymentMethod) }
    var isGooglePayOpened by remember { mutableStateOf(false) }
    val googlePayHelper = GooglePayHelper(
        merchantId = merchantId,
        merchantName = merchantName,
        allowedCardNetworks = ArrayList(method.paymentMethod.availableCardTypes)
    )
    val activity = LocalContext.current as PaymentActivity
    val googlePayClient = remember {
        Wallet.getPaymentsClient(
            activity,
            Wallet.WalletOptions.Builder()
                .setEnvironment(
                    if (paymentOptions.merchantEnvironment == GooglePayEnvironment.TEST)
                        WalletConstants.ENVIRONMENT_TEST
                    else
                        WalletConstants.ENVIRONMENT_PRODUCTION
                )
                .build()
        )
    }
    val handle: (GooglePayActivityContract.Result) -> Unit = { result ->
        if (!result.errorMessage.isNullOrEmpty()) {
            mainViewModel.showError(
                ErrorResult(
                    code = ErrorCode.UNKNOWN,
                    message = result.errorMessage
                )
            )
        } else
            result.token?.let {
                paymentMethodsViewModel.setCurrentMethod(method)
                mainViewModel.payGoogle(
                    actionType = paymentOptions.actionType,
                    method = method,
                    merchantId = merchantId,
                    token = it,
                    environment = paymentOptions.merchantEnvironment,
                    recipientInfo = paymentOptions.recipientInfo,
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
                currency = paymentOptions.paymentInfo.paymentCurrency,
                allowedCardNetworks = ArrayList(method.paymentMethod.availableCardTypes)
            )
        )
    }

    val readyToPayRequest = googlePayHelper.createReadyToPayRequest()
    val allowedPaymentMethods = readyToPayRequest.getJSONArray("allowedPaymentMethods")

    if (!isForcePaymentMethod)
        LaunchedEffect(Unit) {
            val request =
                IsReadyToPayRequest.fromJson(readyToPayRequest.toString())
            googlePayClient.isReadyToPay(request).addOnCompleteListener { completedTask ->
                isGooglePayAvailable = completedTask.isSuccessful
            }
        }

    when {
        customerFields.hasVisibleCustomerFields() -> {
            ExpandablePaymentMethodItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
                fallbackIcon = painterResource(id = SDKTheme.images.googlePayMethodResId),
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                CustomerFields(
                    customerFields = customerFields,
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues,
                    onCustomerFieldsChanged = { fields, isValid ->
                        method.customerFieldValues = fields
                        isCustomerFieldsValid = isValid
                        method.isCustomerFieldsValid = isValid
                    }
                )
                Spacer(modifier = Modifier.height(22.dp))
                GooglePayButton(
                    allowedPaymentMethods = allowedPaymentMethods.toString(),
                    onClick = launchGooglePaySheet,
                    enabled = isCustomerFieldsValid && isGooglePayAvailable && !isGooglePayOpened
                )
                RecurringAgreements()
            }
        }

        customerFields.isAllCustomerFieldsHidden() && (isForcePaymentMethod || isTryAgain) -> {
            method.customerFieldValues = customerFields.mergeHiddenFieldsToList(
                additionalFields = additionalFields,
                customerFieldValues = method.customerFieldValues
            ).map {
                CustomerFieldValue(
                    name = it.name,
                    value = it.value
                )
            }
            GooglePayButton(
                allowedPaymentMethods = allowedPaymentMethods.toString(),
                onClick = launchGooglePaySheet,
                enabled = isGooglePayAvailable && !isGooglePayOpened
            )
        }

        customerFields.isAllCustomerFieldsHidden() -> {
            GooglePayButton(
                allowedPaymentMethods = allowedPaymentMethods.toString(),
                onClick = {
                    method.customerFieldValues = customerFields.mergeHiddenFieldsToList(
                        additionalFields = additionalFields,
                        customerFieldValues = method.customerFieldValues
                    ).map {
                        CustomerFieldValue(
                            name = it.name,
                            value = it.value
                        )
                    }
                    launchGooglePaySheet()
                },
                enabled = isGooglePayAvailable && !isGooglePayOpened
            )
        }
    }
}