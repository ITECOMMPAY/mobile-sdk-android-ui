package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField

@Composable
internal fun EmailCustomerTextField(
    value: String? = null,
    onValueChanged: (CustomerField, String, Boolean) -> Unit,
    customerField: CustomerField,
) {
    BaseCustomerTextField(
        initialValue = value,
        customerField = customerField,
        onValueChanged = onValueChanged,
        keyboardType = KeyboardType.Email,
    )
}