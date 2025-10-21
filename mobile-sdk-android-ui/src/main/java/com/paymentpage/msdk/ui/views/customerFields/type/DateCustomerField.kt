package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.paymentpage.msdk.core.domain.entities.SdkDate
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.utils.MaskVisualTransformation

@Composable
internal fun DateCustomerTextField(
    value: String? = null,
    onValueChanged: (CustomerField, String, Boolean) -> Unit,
    customerField: CustomerField,
) {
    BaseCustomerTextField(
        initialValue = value?.replace("-", ""),
        customerField = customerField,
        onValueChanged = onValueChanged,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { filteredValue -> filteredValue.filter { it.isDigit() } },
        maxLength = 8,
        visualTransformation = MaskVisualTransformation("##-##-####"),
        onTransformValueBeforeValidate = { SdkDate(it).stringValue }
    )
}