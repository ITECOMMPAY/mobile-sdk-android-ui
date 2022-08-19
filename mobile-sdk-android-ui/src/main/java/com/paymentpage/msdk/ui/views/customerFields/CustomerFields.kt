@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.views.customerFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.FieldServerType
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.views.customerFields.model.UICustomerFieldValue
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    customerFields: List<CustomerField>, //all fields for render
    additionalFields: List<SDKAdditionalField> = emptyList(), //additional fields
    customerFieldValues: List<CustomerFieldValue> = emptyList(), //remembered customerFields
    onCustomerFieldsChanged: (List<CustomerFieldValue>, Boolean) -> Unit, //callback
) {
    val changedFieldsMap = remember {
        customerFields.associate { customerField ->
            val foundAdditionalField =
                additionalFields.firstOrNull { customerField.type == it.type } //find field from additional data
            val foundCustomerFieldValue =
                customerFieldValues.firstOrNull { it.name == customerField.name } //find field from remembered data
            val fieldValue = foundCustomerFieldValue?.value ?: foundAdditionalField?.value
            customerField.name to UICustomerFieldValue(
                name = customerField.name,
                value = fieldValue ?: "",
                isRequired = customerField.isRequired,
                isValid = (!customerField.isRequired && fieldValue.isNullOrEmpty()) || (customerField.isRequired && !fieldValue.isNullOrEmpty()) //if field is not required and empty or required but not empty
            )
        }.toMutableMap()
    }

    LaunchedEffect(Unit) {
        val isAllFieldsValid = changedFieldsMap.values.none { !it.isValid }
        onCustomerFieldsChanged(
            changedFieldsMap.values.map { CustomerFieldValue(name = it.name, value = it.value) },
            isAllFieldsValid
        )
    }

    val fieldChanged: (CustomerField, String, Boolean) -> Unit = { customerField, value, isValid ->

        changedFieldsMap[customerField.name] =
            changedFieldsMap[customerField.name]?.copy(value = value, isValid = isValid)
                ?: UICustomerFieldValue(
                    name = customerField.name,
                    value = "",
                    isRequired = customerField.isRequired,
                    isValid = false
                )

        val isAllFieldsValid = changedFieldsMap.values.none { !it.isValid }
        onCustomerFieldsChanged(
            changedFieldsMap.values.map { CustomerFieldValue(name = it.name, value = it.value) },
            isAllFieldsValid
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        customerFields.forEachIndexed { index, field ->
            if (index < customerFields.size)
                Spacer(modifier = Modifier.size(10.dp))
            when (field.serverType) {
                FieldServerType.TEL -> {
                    TelCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.DATE -> {
                    DateCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.NUMBER -> {
                    NumberCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.PASSWORD -> {
                    PasswordCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.EMAIL -> {
                    EmailCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
                else -> {
                    TextCustomerTextField(
                        value = changedFieldsMap[field.name]?.value,
                        onValueChanged = { customerField, value, isValid ->
                            fieldChanged(
                                customerField,
                                value,
                                isValid
                            )
                        },
                        customerField = field
                    )
                }
            }
        }
    }

}