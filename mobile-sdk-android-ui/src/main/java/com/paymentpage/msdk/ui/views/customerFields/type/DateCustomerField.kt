package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.paymentpage.msdk.ui.utils.MaskVisualTransformation
import com.paymentpage.msdk.core.domain.entities.SdkDate
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue

@Composable
fun DateCustomerTextField(
    value: String? = null,
    onValueChanged: (CustomerFieldValue) -> Unit = {},
    customerField: CustomerField
) {
    BaseCustomerTextField(
        initialValue = value?.replace("-", ""),
        customerField = customerField,
        onValueChanged = onValueChanged,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        maxLength = 8,
        visualTransformation = MaskVisualTransformation("##-##-####"),
        onTransformValueBeforeValidate = {
            val sdkDate = SdkDate(it)
            val str = sdkDate.stringValue
            str
        }
    )
}