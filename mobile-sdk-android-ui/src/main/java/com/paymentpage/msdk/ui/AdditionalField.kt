@file:Suppress("unused")

package com.paymentpage.msdk.ui

import com.paymentpage.msdk.core.domain.entities.field.FieldType
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl

@PaymentOptionsDsl
class AdditionalField {
    var type: FieldType = FieldType.UNKNOWN
    var value: String? = null
}

@PaymentOptionsDsl
class AdditionalFields : ArrayList<AdditionalField>() {
    fun field(block: AdditionalField.() -> Unit) {
        add(AdditionalField().apply(block))
    }
}