package com.paymentpage.ui.msdk.sample.domain.entities

import android.graphics.Bitmap
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.SDKMockModeType
import java.util.*

data class PaymentData(
    val brandColor: String = "#00579E",
    val bitmap: Bitmap? = null,
    val projectId: Int = 111781,
    val paymentId: String = "sdk_sample_ui_${UUID.randomUUID().toString().take(8)}",
    val paymentAmount: Long = 123,
    val paymentCurrency: String = "USD",
    val customerId: String = "12",
    val paymentDescription: String = "Test payment",
    val languageCode: String = "",
    val forcePaymentMethod: String = "",
    val hideSavedWallets: Boolean = false,
    val secretKey: String = "123",
    val apiHost: String = "pp-sdk.westresscode.net",
    val wsApiHost: String = "paymentpage.westresscode.net",
    val merchantId: String = "BCR2DN6TZ75OBLTH",
    val merchantName: String = "Example Merchant",
    val mockModeType: SDKMockModeType = SDKMockModeType.DISABLED,
    val actionType: SDKActionType = SDKActionType.Sale
)
