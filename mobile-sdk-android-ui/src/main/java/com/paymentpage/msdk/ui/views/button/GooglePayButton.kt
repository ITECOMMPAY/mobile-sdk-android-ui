package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.android.gms.wallet.*
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.googlePay.GooglePayHelper
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.views.common.CustomButton
import java.math.BigDecimal

@Composable
internal fun GooglePayButton(isEnabled: Boolean, onComplete: () -> Unit) {

    val paymentOptions = LocalPaymentOptions.current
    val paymentInfo = paymentOptions.paymentInfo

    var isGooglePayAvailable by remember { mutableStateOf(false) }
    val googlePayHelper = GooglePayHelper(
        LocalPaymentOptions.current.merchantId!!,
        LocalPaymentOptions.current.merchantName!!
    )

    val activity = LocalContext.current as PaymentActivity
    val googlePayClient = remember {
        Wallet.getPaymentsClient(
            activity,
            Wallet.WalletOptions.Builder()
                .setEnvironment(if (paymentOptions.merchantEnvironment == GooglePayEnvironment.TEST) WalletConstants.ENVIRONMENT_TEST else WalletConstants.ENVIRONMENT_PRODUCTION)//test
                .setTheme(WalletConstants.THEME_LIGHT)
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

//    val launcher =
//        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            result.data?.let {
//                val paymentData = PaymentData.getFromIntent(it)
//                val paymentInformation = paymentData?.toJson() ?: return@let
//                val paymentMethodData: JSONObject =
//                    JSONObject(paymentInformation).getJSONObject("paymentMethodData")
//                val token = paymentMethodData.getJSONObject("tokenizationData").getString("token")
//            }
//        }

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
            val googleJson =
                googlePayHelper.createPaymentDataRequest(
                    BigDecimal.valueOf(
                        paymentInfo.paymentAmount / 100
                    ), paymentInfo.paymentCurrency
                ).toString()
            val gpayRequest = PaymentDataRequest.fromJson(googleJson)

            AutoResolveHelper.resolveTask(
                googlePayClient.loadPaymentData(gpayRequest),
                activity,
                Constants.GOOGLE_PAY_ACTIVITY_REQUEST_CODE
            )
        }
    )

}