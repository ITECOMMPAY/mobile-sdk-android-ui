package com.paymentpage.msdk.ui.utils.extensions.core

import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.views.customerFields.model.UICustomerFieldValue

internal fun CustomerField.validate(
    value: String,
    onTransformValueBeforeValidate: ((String) -> String)?
): String? {
    val validator = this.validator
    var resultMessage: String? = null
    val trimmedValue = value.trim()
    if (this.isRequired && trimmedValue.isEmpty()) {
        resultMessage =
            getStringOverride(OverridesKeys.MESSAGE_REQUIRED_FIELD)
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

internal fun List<CustomerField>.hiddenCustomerFields(): List<CustomerField> {
    return this.filter { it.isHidden }
}


internal fun List<CustomerField>.needSendWithSaleRequest(): Boolean {
    return visibleCustomerFields().size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
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


internal fun List<CustomerField>.mergeVisibleFieldsToList(
    additionalFields: List<SDKAdditionalField>,
    customerFieldValues: List<CustomerFieldValue>,
): List<UICustomerFieldValue> {
    return visibleCustomerFields().map { customerField ->
        val foundAdditionalField =
            additionalFields.firstOrNull { customerField.name == it.type?.value } //find field from additional data
        val foundCustomerFieldValue =
            customerFieldValues.firstOrNull { it.name == customerField.name } //find field from remembered data
        val fieldValue = (foundCustomerFieldValue?.value ?: foundAdditionalField?.value) ?: ""

        val validator = customerField.validator
        UICustomerFieldValue(
            name = customerField.name,
            value = fieldValue,
            isRequired = customerField.isRequired,
            isHidden = customerField.isHidden,
            isValid = (
                    fieldValue.isNotEmpty()
                            && validator != null
                            && validator.isValid(fieldValue)
                    ) //field is not empty and has validator and value is valid
                    || (!customerField.isRequired && fieldValue.isEmpty()) // field is not required and empty
                    || (fieldValue.isNotEmpty() && validator == null) //field not empty without validator
        )
    }
}

internal fun List<CustomerField>.mergeHiddenFieldsToList(
    additionalFields: List<SDKAdditionalField>,
    customerFieldValues: List<CustomerFieldValue>,
): List<UICustomerFieldValue> {
    return hiddenCustomerFields().map { customerField ->
        val foundAdditionalField =
            additionalFields.firstOrNull { customerField.name == it.type?.value } //find field from additional data
        val foundCustomerFieldValue =
            customerFieldValues.firstOrNull { it.name == customerField.name } //find field from remembered data
        val fieldValue = (foundCustomerFieldValue?.value ?: foundAdditionalField?.value) ?: ""

        UICustomerFieldValue(
            name = customerField.name,
            value = fieldValue,
            isRequired = false,
            isHidden = customerField.isHidden,
            isValid = true
        )
    }
}