package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField

@Composable
internal fun TextCustomerTextField(
    value: String? = null,
    onValueChanged: (CustomerField, String, Boolean) -> Unit,
    customerField: CustomerField
) {
    BaseCustomerTextField(
        initialValue = value,
        customerField = customerField,
        onValueChanged = onValueChanged,
    )
}