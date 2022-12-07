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
import com.paymentpage.msdk.ui.utils.extensions.core.mergeHiddenFieldsToList
import com.paymentpage.msdk.ui.utils.extensions.core.mergeVisibleFieldsToList
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.views.customerFields.model.UICustomerFieldValue
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    customerFields: List<CustomerField>, //all fields for render
    additionalFields: List<SDKAdditionalField> = emptyList(), //additional fields
    customerFieldValues: List<CustomerFieldValue> = emptyList(), //remembered previously fields
    onCustomerFieldsChanged: (List<CustomerFieldValue>, Boolean) -> Unit, //callback (all fields, is all valid)
) {
    val changedFieldsMap = remember {
        customerFields.mergeVisibleFieldsToList(
            additionalFields = additionalFields,
            customerFieldValues = customerFieldValues
        ).associateBy { it.name }.toMutableMap()
    }
    val visibleCustomerFields = remember { customerFields.visibleCustomerFields() }
    val mergedHiddenFields = remember {
        customerFields.mergeHiddenFieldsToList(
            additionalFields = additionalFields,
            customerFieldValues = customerFieldValues
        ).map {
            CustomerFieldValue(
                name = it.name,
                value = it.value
            )
        }
    }

    LaunchedEffect(Unit) {
        val isAllFieldsValid = changedFieldsMap.values.none { !it.isValid } //if all fields is valid
        val resultList = mutableListOf<CustomerFieldValue>()
        resultList.addAll(changedFieldsMap.values.map {
            CustomerFieldValue(
                name = it.name,
                value = it.value
            )
        })
        resultList.addAll(mergedHiddenFields)
        //call once at first time
        onCustomerFieldsChanged(
            resultList.toList(),
            isAllFieldsValid
        )
    }

    val fieldChanged: (CustomerField, String, Boolean) -> Unit = remember {
        { customerField, value, isValid ->

            changedFieldsMap[customerField.name] =
                changedFieldsMap[customerField.name]?.copy(value = value, isValid = isValid)
                    ?: UICustomerFieldValue(
                        name = customerField.name,
                        value = "",
                        isHidden = customerField.isHidden,
                        isRequired = customerField.isRequired,
                        isValid = !customerField.isRequired || customerField.isHidden
                    )

            val isAllFieldsValid =
                changedFieldsMap.values.none { !it.isValid } //if all fields is valid

            val resultList = mutableListOf<CustomerFieldValue>()
            resultList.addAll(changedFieldsMap.values.map {
                CustomerFieldValue(
                    name = it.name,
                    value = it.value
                )
            })
            resultList.addAll(mergedHiddenFields)

            onCustomerFieldsChanged(
                resultList.toList(),
                isAllFieldsValid
            )
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        visibleCustomerFields.forEachIndexed { index, field ->
            if (index < visibleCustomerFields.size)
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