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
    val paymentInfo: PaymentInfo,

    val recurrentInfo: RecurrentInfo? = null,
    val recipientInfo: RecipientInfo? = null,
    val actionType: SDKActionType = SDKActionType.Sale,
    //val bankId: Int? = null,

    val logoImage: Bitmap? = null,
    val brandColor: String? = null,

    val merchantId: String = "",
    val merchantName: String = "",
    val merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST,

    val additionalFields: List<SDKAdditionalField> = emptyList()
)