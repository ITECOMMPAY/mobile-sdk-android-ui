@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.views.customerFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.FieldServerType
import com.paymentpage.msdk.core.domain.entities.field.FieldType
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    customerFields: List<CustomerField>,
    additionalFields: List<AdditionalField> = emptyList(),
    onCustomerFieldsSuccess: (List<CustomerFieldValue>) -> Unit = {},
    onCustomerFieldsError: () -> Unit = {}
) {
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    //val visibleRequiredCustomerFields = remember { visibleCustomerFields.filter { it.isRequired } }

    Column(modifier = Modifier.fillMaxWidth()) {
        visibleCustomerFields.forEachIndexed { index, field ->
            if (index < customerFields.size - 1)
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            val foundAdditionalField =
                additionalFields.firstOrNull { it.type == FieldType.from(field.type.value) }
            when (field.serverType) {
                FieldServerType.TEL ->
                    TelCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
                FieldServerType.DATE ->
                    DateCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
                FieldServerType.NUMBER ->
                    NumberCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
                FieldServerType.PASSWORD ->
                    PasswordCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
                FieldServerType.EMAIL ->
                    EmailCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
                else ->
                    TextCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = {},
                        customerField = field
                    )
            }
        }
    }

}
