@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.ecommpay.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl


fun paymentOptions(block: PaymentOptions.() -> Unit): PaymentOptions = PaymentOptions().apply(block)

/**
 * Payment configuration
 */
@PaymentOptionsDsl
class PaymentOptions {
    lateinit var paymentInfo: EcmpPaymentInfo

    var recurrentData: EcmpRecurrentData? = null
    var threeDSecureInfo: ThreeDSecureInfo? = null
    var recipientInfo: RecipientInfo? = null
    var actionType: EcmpActionType = EcmpActionType.Sale
    //var bankId: Int? = null

    var logoImage: Bitmap? = null
    var brandColor: String? = null

    var merchantId: String = ""
    var merchantName: String = ""
    var isTestEnvironment: Boolean = true

    var additionalFields = mutableListOf<EcmpAdditionalField>()
    fun additionalFields(block: EcmpAdditionalFields.() -> Unit) {
        additionalFields.addAll(EcmpAdditionalFields().apply(block))
    }
}