package com.ecommpay.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.ecommpay.msdk.core.domain.entities.customer.CustomerField
import com.ecommpay.msdk.core.domain.entities.customer.CustomerFieldValue
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
fun BaseCustomerTextField(
    initialValue: String?,
    onValueChanged: (CustomerFieldValue) -> Unit = {},
    customerField: CustomerField,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFilterValueBefore: ((String) -> String)? = null,
    onTransformValueBeforeValidate: ((String) -> String)? = null,
    maxLength: Int? = null
) {
    CustomTextField(
        modifier = Modifier,
        maxLength = maxLength,
        initialValue = initialValue,
        onValidate = {
            val validator = customerField.validator
            var resultMessage: String? = null
            if (customerField.isRequired && it.isEmpty()) {
                resultMessage =
                    PaymentActivity.stringResourceManager.getStringByKey("message_required_field")
            } else if (validator != null) {
                val text = if (onTransformValueBeforeValidate != null)
                    onTransformValueBeforeValidate(it)
                else
                    it
                if (!validator.isValid(text))
                    resultMessage = customerField.errorMessage ?: customerField.errorMessageKey
            }
            resultMessage
        },
        onValueChanged = {
            onValueChanged(CustomerFieldValue.fromTypeWithValue(customerField.type, it))
        },
        label = customerField.label,
        placeholder = customerField.placeholder ?: customerField.hint,
        isRequired = customerField.isRequired,
        onFilterValueBefore = onFilterValueBefore,
        visualTransformation = visualTransformation,
        keyboardType = keyboardType
    )
}