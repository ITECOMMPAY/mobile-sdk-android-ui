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
    lateinit var paymentData: PaymentData

    var recurrentData: RecurrentData? = null
    var threeDSecureInfo: ThreeDSecureInfo? = null
    var recipientInfo: RecipientInfo? = null
    var actionType: ActionType = ActionType.Sale
    //var bankId: Int? = null

    var logoImage: Bitmap? = null
    var brandColor: String? = null

    var merchantId: String = ""
    var merchantName: String = ""
    var isTestEnvironment: Boolean = true

    var additionalFields = mutableListOf<AdditionalField>()
    fun additionalFields(block: AdditionalFields.() -> Unit) {
        additionalFields.addAll(AdditionalFields().apply(block))
    }
}