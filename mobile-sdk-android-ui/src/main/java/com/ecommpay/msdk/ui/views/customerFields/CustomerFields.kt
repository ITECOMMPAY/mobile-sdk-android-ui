@file:Suppress("UNUSED_PARAMETER")

package com.ecommpay.msdk.ui.views.customerFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ecommpay.msdk.core.domain.entities.customer.CustomerField
import com.ecommpay.msdk.core.domain.entities.customer.CustomerFieldServerType
import com.ecommpay.msdk.ui.AdditionalField
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    customerFields: List<CustomerField>,
    additionalFields: List<AdditionalField> = emptyList()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        customerFields.filter { !it.isHidden }.forEach { field ->

            when (field.serverType) {
                CustomerFieldServerType.TEL ->
                    TelCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
                CustomerFieldServerType.DATE ->
                    DateCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
                CustomerFieldServerType.NUMBER ->
                    NumberCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
                CustomerFieldServerType.PASSWORD ->
                    PasswordCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
                CustomerFieldServerType.EMAIL ->
                    EmailCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
                else ->
                    TextCustomerTextField(
                        value = if (additionalFields.isNotEmpty()) additionalFields.first { it.type == field.type }.value else null,
                        onValueChange = {},
                        customerField = field
                    )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        }
    }

}
