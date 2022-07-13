@file:Suppress("unused")

package com.ecommpay.msdk.ui

import com.ecommpay.msdk.core.domain.entities.field.FieldType


@PaymentOptionsDsl
class AdditionalField {
    var type: FieldType = FieldType.UNKNOWN
    var value: String = ""
}

@PaymentOptionsDsl
class AdditionalFields : ArrayList<AdditionalField>() {

    fun field(block: AdditionalField.() -> Unit) {
        add(AdditionalField().apply(block))
    }

}