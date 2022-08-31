@file:Suppress("unused")

package com.paymentpage.msdk.ui

import com.paymentpage.msdk.core.domain.entities.field.FieldType

class SDKAdditionalField(
    var type: FieldType = FieldType.UNKNOWN,
    var value: String? = null,
)