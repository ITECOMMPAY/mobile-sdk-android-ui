package com.paymentpage.ui.msdk.sample.ui.presentation.main.models

import android.graphics.Bitmap
import com.ecommpay.msdk.ui.PaymentSDK
import java.util.*

data class PaymentData(
    val brandColor: String?,
    val bitmap: Bitmap?,
    val projectId: Int?,
    val paymentId: String,
    val paymentAmount: Long?,
    val paymentCurrency: String,
    val customerId: String,
    val paymentDescription: String,
    val languageCode: String,
    val forcePaymentMethod: String?,
    val hideSavedWallets: Boolean,
    val secretKey: String,
    val apiHost: String,
    val wsApiHost: String,
    val merchantId: String,
    val merchantName: String,
    val mockModeType: PaymentSDK.MockModeType,
) {
    companion object {
        val defaultPaymentData = PaymentData(
            brandColor = "#00579E",
            bitmap = null,
            projectId = 111781,
            paymentId = "sdk_sample_ui_${UUID.randomUUID().toString().take(8)}",
            paymentAmount = 123,
            paymentCurrency = "USD",
            customerId = "12",
            paymentDescription = "Test payment",
            languageCode = "",
            forcePaymentMethod = null,
            hideSavedWallets = false,
            secretKey = "123",
            apiHost = "pp-sdk.westresscode.net",
            wsApiHost = "paymentpage.westresscode.net",
            merchantId = "BCR2DN6TZ75OBLTH",
            merchantName = "Example Merchant",
            mockModeType = PaymentSDK.MockModeType.DISABLED
        )
    }
}
