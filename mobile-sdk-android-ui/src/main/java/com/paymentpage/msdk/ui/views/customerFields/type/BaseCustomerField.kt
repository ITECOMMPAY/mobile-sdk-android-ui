package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.utils.extensions.core.validate
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun BaseCustomerTextField(
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
        maxLength = maxLength,
        initialValue = initialValue,
        onRequestValidatorMessage = {
            val errorMessage = customerField.validate(it, onTransformValueBeforeValidate)

            errorMessage
        },
        onValueChanged = { text, isValid ->
            var isResultValid = isValid
            if (text.isNotEmpty()) //if field not empty - need validate text
                isResultValid = isResultValid && (customerField.validate(
                    text,
                    onTransformValueBeforeValidate
                ) == null)
            onValueChanged(customerField, text, isResultValid)
        },
        label = customerField.label,
        placeholder = customerField.placeholder ?: customerField.hint,
        isRequired = customerField.isRequired,
        onFilterValueBefore = onFilterValueBefore,
        visualTransformation = visualTransformation,
        keyboardType = keyboardType,
        testTag = "${
            customerField.label.uppercase()
        }${
            TestTagsConstants.POSTFIX_CUSTOMER_FIELD
        }"
    )
}