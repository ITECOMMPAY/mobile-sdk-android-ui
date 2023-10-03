package com.ecommpay.ui.msdk.sample.domain.entities

import com.ecommpay.ui.msdk.sample.BuildConfig
import java.util.UUID

data class PaymentData(
    val projectId: Int = 185541,
    val paymentId: String = "sdk_sample_ui_${UUID.randomUUID().toString().take(8)}",
    val paymentAmount: Long =  11001,
    val paymentCurrency: String = "USD",
    val customerId: String? = "12",
    val paymentDescription: String? = "Test payment",
    val languageCode: String? = null,
    val regionCode: String? = null,
    val forcePaymentMethod: String? = null,
    val token: String? = null,
    val hideSavedWallets: Boolean = false,
    val secretKey: String = "123",
    val apiHost: String = BuildConfig.API_HOST,
    val wsApiHost: String = BuildConfig.WS_API_HOST,
    val merchantId: String = "BCR2DN6TZ75OBLTH",
    val merchantName: String = "Example Merchant",
)
