@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.paymentpage.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment


/**
 * Payment configuration
 */
class SDKPaymentOptions(
    //payment configuration
    val paymentInfo: PaymentInfo,
    val recurrentInfo: RecurrentInfo? = null,
    val recipientInfo: RecipientInfo? = null,
    val actionType: SDKActionType = SDKActionType.Sale,
    //val bankId: Int? = null,
    val screenDisplayModes: List<SDKScreenDisplayMode> = listOf(SDKScreenDisplayMode.DEFAULT),
    val merchantId: String = "",
    val merchantName: String = "",
    val merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST,
    val additionalFields: List<SDKAdditionalField> = emptyList(),
    val hideScanningCards: Boolean = false,
    //theme customization
    val isDarkTheme: Boolean = false,
    val logoImage: Bitmap? = null,
    var brandColor: String? = null,
    var footerImage: Bitmap? = null,
    var footerLabel: String? = null,
    //stored card type
    var storedCardType: Int? = null
)
