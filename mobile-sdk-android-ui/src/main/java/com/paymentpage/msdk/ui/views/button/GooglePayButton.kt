package com.paymentpage.msdk.ui.views.button

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.googlePay.GooglePayHelper
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.googlePay.GooglePayActivityContract
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun GooglePayButton(
    isEnabled: Boolean,
    onComplete: (GooglePayActivityContract.Result) -> Unit
) {

    val paymentOptions = LocalPaymentOptions.current
    val merchantId = LocalPaymentOptions.current.merchantId
    val merchantName = LocalPaymentOptions.current.merchantName

    var isGooglePayAvailable by remember { mutableStateOf(false) }
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

    LaunchedEffect(Unit) {
        val request =
            IsReadyToPayRequest.fromJson(googlePayHelper.createReadyToPayRequest().toString())
        googlePayClient.isReadyToPay(request).addOnCompleteListener { completedTask ->
            isGooglePayAvailable = completedTask.isSuccessful
        }
    }

    val launcher =
        rememberLauncherForActivityResult(GooglePayActivityContract()) { result ->
            onComplete(result)
        }

    CustomButton(
        modifier = Modifier
            .height(LocalDimensions.current.googlePayButtonHeight)
            .fillMaxWidth(),
        isEnabled = isEnabled && isGooglePayAvailable,
        content = {
            Image(
                painter = painterResource(id = R.drawable.googlepay_button_logo),
                contentDescription = null
            )
        },
        color = Color.Black,
        onClick = {

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
    )

}