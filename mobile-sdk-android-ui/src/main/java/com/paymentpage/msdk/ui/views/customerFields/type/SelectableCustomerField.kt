package com.paymentpage.msdk.ui.views.customerFields.type

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomTextField
import com.paymentpage.msdk.ui.views.common.SelectItemsDialog

@Composable
internal fun SelectableCustomerField(
    items: Map<String?, String?>,
    initialValue: String? = null,
    onValueChanged: (CustomerField, String, Boolean) -> Unit,
    customerField: CustomerField,
) {
    var selectedText by remember { mutableStateOf(initialValue ?: "") }
    var dialogState by remember { mutableStateOf(false) }
    CustomTextField(
        value = selectedText,
        initialValue = initialValue,
        onValueChanged = null,
        isEditable = false,
        label = customerField.label,
        placeholder = customerField.placeholder ?: customerField.hint,
        isRequired = customerField.isRequired,
        trailingIcon = {
            Image(
                modifier = Modifier.clickable {
                    dialogState = true
                },
                imageVector = Icons.Default.KeyboardArrowDown,
                colorFilter = ColorFilter.tint(SDKTheme.colors.navigationIconColor),
                contentDescription = null,
            )
        },
    )
    if (dialogState)
        SelectItemsDialog(
            modifier = Modifier.size(width = 400.dp, height = 300.dp),
            items = items,
            onDismissRequest = { dialogState = false }
        ) { key ->
            selectedText = items[key] ?: ""
            onValueChanged(
                customerField,
                selectedText,

                //validation
                if (customerField.isRequired)
                    selectedText.isNotEmpty()
                else true

            )
            dialogState = false
        }
}