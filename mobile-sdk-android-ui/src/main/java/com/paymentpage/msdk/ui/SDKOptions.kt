@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.paymentpage.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl


/**
 * Payment configuration
 */
class SDKOptions(
    val paymentInfo: PaymentInfo,

    val recurrentInfo: RecurrentInfo? = null,
    //var threeDSecureInfo: ThreeDSecureInfo? = null
    val recipientInfo: RecipientInfo? = null,
    val actionType: ActionType = ActionType.Sale,
    //val bankId: Int? = null,

    val logoImage: Bitmap? = null,
    val brandColor: String? = null,

    val merchantId: String = "",
    val merchantName: String = "",
    val merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST,

    val additionalFields: List<SDKAdditionalField> = mutableListOf<SDKAdditionalField>()
)