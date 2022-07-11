@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.ecommpay.msdk.ui

import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.RecurrentInfo
import com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo

enum class ActionType {
    Sale, Auth, Tokenize, Verify
}

@DslMarker
annotation class PaymentOptionsDsl

fun paymentOptions(block: PaymentOptions.() -> Unit): PaymentOptions = PaymentOptions().apply(block)

/**
 * Payment configuration
 */
@PaymentOptionsDsl
class PaymentOptions {
    var paymentInfo: PaymentInfo? = null
    var recurrentInfo: RecurrentInfo? = null
    var threeDSecureInfo: ThreeDSecureInfo? = null
    var actionType: ActionType = ActionType.Sale
    var bankId: Int? = null
    var merchantId: String? = null

    var additionalFields = mutableListOf<AdditionalField>()
    fun additionalFields(block: AdditionalFields.() -> Unit) {
        additionalFields.addAll(AdditionalFields().apply(block))
    }
}