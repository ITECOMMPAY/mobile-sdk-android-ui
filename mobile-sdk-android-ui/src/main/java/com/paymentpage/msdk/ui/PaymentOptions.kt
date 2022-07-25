@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.paymentpage.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.ui.base.ActionType
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl


fun paymentOptions(block: PaymentOptions.() -> Unit): PaymentOptions = PaymentOptions().apply(block)

/**
 * Payment configuration
 */
@PaymentOptionsDsl
class PaymentOptions {
    lateinit var paymentInfo: PaymentInfo

    var recurrentInfo: RecurrentInfo? = null
    var threeDSecureInfo: ThreeDSecureInfo? = null
    var recipientInfo: RecipientInfo? = null
    var actionType: ActionType = ActionType.Sale
    var bankId: Int? = null

    var logoImage: Bitmap? = null
    var brandColor: String? = null

    var merchantId: String? = null
    var merchantName: String? = null
    var merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST

    var additionalFields = mutableListOf<AdditionalField>()
    fun additionalFields(block: AdditionalFields.() -> Unit) {
        additionalFields.addAll(AdditionalFields().apply(block))
    }
    fun check(){
        if (!::paymentInfo.isInitialized)
            throw  IllegalArgumentException("PaymentInfo not initialized")
    }
}