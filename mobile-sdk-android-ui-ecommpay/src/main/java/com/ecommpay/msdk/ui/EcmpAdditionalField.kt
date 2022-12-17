@file:Suppress("unused")

package com.ecommpay.msdk.ui

import com.paymentpage.msdk.ui.base.PaymentOptionsDsl

@PaymentOptionsDsl
class EcmpAdditionalField(
    var type: EcmpAdditionalFieldType? = null,
    var value: String? = null,
)

@PaymentOptionsDsl
class EcmpAdditionalFields : ArrayList<EcmpAdditionalField>() {
    fun field(block: EcmpAdditionalField.() -> Unit) {
        add(EcmpAdditionalField().apply(block))
    }
}