package com.mglwallet.ui.msdk.sample.domain.entities

import com.mglwallet.ui.msdk.sample.BuildConfig
import java.util.UUID

data class PaymentData(
    val projectId: Int = 149421,
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
    val secretKey: String = "1d2bd69318a1e3c77a0cb3f608c0b53bf1778d0e886fc776f361348cb8be7d38425cdd722524eca922c0031d40eef83d91a7d15c69015520933bec01f84c1c31",
    val apiHost: String = BuildConfig.API_HOST,
    val wsApiHost: String = BuildConfig.WS_API_HOST,
    val merchantId: String = "BCR2DN6TZ75OBLTH",
    val merchantName: String = "Example Merchant",
)
