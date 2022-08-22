package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.PaymentActivity

internal fun CustomerField.validate(
    value: String,
    onTransformValueBeforeValidate: ((String) -> String)?
): String? {
    val validator = this.validator
    var resultMessage: String? = null
    if (this.isRequired && value.isEmpty()) {
        resultMessage =
            PaymentActivity.stringResourceManager.getStringByKey("message_required_field")
    } else if (validator != null) {
        val text = if (onTransformValueBeforeValidate != null)
            onTransformValueBeforeValidate(value)
        else
            value
        if (!validator.isValid(text))
            resultMessage = this.errorMessage ?: this.errorMessageKey
    }
    return resultMessage
}


internal fun List<CustomerField>.visibleCustomerFields(): List<CustomerField> {
    return this.filter { !it.isHidden }
}

internal fun List<CustomerField>.isAllCustomerFieldsHidden(): Boolean {
    return !this.any { !it.isHidden }
}

internal fun List<CustomerField>.isAllCustomerFieldsNonRequired(): Boolean {
    return !this.any { it.isRequired }
}

internal fun List<CustomerField>.hasVisibleCustomerFields(): Boolean {
    return this.any { !it.isHidden }
}