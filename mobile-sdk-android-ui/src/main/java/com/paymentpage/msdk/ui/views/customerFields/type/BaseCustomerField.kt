package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.utils.extensions.core.validate
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
fun BaseCustomerTextField(
    initialValue: String?,
    onValueChanged: (CustomerField, String, Boolean) -> Unit,
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
        onRequestValidatorMessage = {
            customerField.validate(it, onTransformValueBeforeValidate)
        },
        onValueChanged = { text, isValid ->
            onValueChanged(customerField, text, isValid && (customerField.validate(text, onTransformValueBeforeValidate)) == null)
        },
        label = customerField.label,
        placeholder = customerField.placeholder ?: customerField.hint,
        isRequired = customerField.isRequired,
        onFilterValueBefore = onFilterValueBefore,
        visualTransformation = visualTransformation,
        keyboardType = keyboardType
    )
}