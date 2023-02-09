package com.ecommpay.ui.msdk.sample.domain.entities

import android.graphics.Bitmap
import com.ecommpay.ui.msdk.sample.BuildConfig
import java.util.*

data class PaymentData(
    val brandColor: String? = "#00579E",
    val bitmap: Bitmap? = null,
    val projectId: Int = 111781,
    val paymentId: String = "sdk_sample_ui_${UUID.randomUUID().toString().take(8)}",
    val paymentAmount: Long = 123,
    val paymentCurrency: String = "USD",
    val customerId: String? = "12",
    val paymentDescription: String? = "Test payment",
    val languageCode: String? = null,
    val forcePaymentMethod: String? = null,
    val token: String? = null,
    val hideSavedWallets: Boolean = false,
    val secretKey: String = "123",
    val apiHost: String = BuildConfig.API_HOST,
    val wsApiHost: String = BuildConfig.WS_API_HOST,
    val merchantId: String = "BCR2DN6TZ75OBLTH",
    val merchantName: String = "Example Merchant",
)
