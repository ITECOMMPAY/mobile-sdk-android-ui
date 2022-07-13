package com.ecommpay.msdk.ui.views.customerFields.type

import androidx.compose.runtime.Composable
import com.ecommpay.msdk.core.domain.entities.customer.CustomerField
import com.ecommpay.msdk.core.domain.entities.customer.CustomerFieldValue

@Composable
fun TextCustomerTextField(
    value: String?,
    onValueChange: (CustomerFieldValue) -> Unit = {},
    customerField: CustomerField
) {
    BaseCustomerTextField(
        initialValue = value,
        customerField = customerField,
        onValueChange = onValueChange,
    )
}