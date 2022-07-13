@file:Suppress("unused")

package com.ecommpay.msdk.ui

import com.ecommpay.msdk.core.domain.entities.customer.CustomerFieldType

@PaymentOptionsDsl
class AdditionalField {
    var type: CustomerFieldType = CustomerFieldType.UNKNOWN
    var value: String = ""
}

@PaymentOptionsDsl
class AdditionalFields : ArrayList<AdditionalField>() {

    fun field(block: AdditionalField.() -> Unit) {
        add(AdditionalField().apply(block))
    }

}