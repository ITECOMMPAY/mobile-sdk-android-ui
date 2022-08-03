@file:Suppress("unused")

package com.ecommpay.msdk.ui

import com.paymentpage.msdk.ui.base.PaymentOptionsDsl

@PaymentOptionsDsl
class AdditionalField(
    var type: AdditionalFieldType? = null,
    var value: String? = null,
)

@PaymentOptionsDsl
class AdditionalFields : ArrayList<AdditionalField>() {
    fun field(block: AdditionalField.() -> Unit) {
        add(AdditionalField().apply(block))
    }
}