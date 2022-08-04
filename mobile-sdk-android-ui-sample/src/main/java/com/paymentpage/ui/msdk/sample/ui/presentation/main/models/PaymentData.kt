package com.paymentpage.ui.msdk.sample.ui.presentation.main.models

import android.graphics.Bitmap
import com.ecommpay.msdk.ui.EcmpPaymentMethodType
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
    val languageCode: String?,
    val forcePaymentMethod: EcmpPaymentMethodType?,
    val hideSavedWallets: Boolean,
    val secretKey: String,
    val apiHost: String,
    val wsApiHost: String,
    val merchantId: String,
    val merchantName: String,
    val mockModeEnabled: Boolean,
) {
    companion object {
        val defaultPaymentData = PaymentData(
            brandColor = "#00579E",
            bitmap = null,
            projectId = 111781,
            paymentId = UUID.randomUUID().toString().take(30),
            paymentAmount = 123,
            paymentCurrency = "USD",
            customerId = "12",
            paymentDescription = "Test payment",
            languageCode = null,
            forcePaymentMethod = null,
            hideSavedWallets = false,
            secretKey = "123",
            apiHost = "pp-sdk-3.westresscode.net",
            wsApiHost = "paymentpage-3.westresscode.net",
            merchantId = "BCR2DN6TZ75OBLTH",
            merchantName = "Example Merchant",
            mockModeEnabled = false
        )
    }
}
