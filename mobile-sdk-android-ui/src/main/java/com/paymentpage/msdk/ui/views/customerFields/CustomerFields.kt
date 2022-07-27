@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.views.customerFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.FieldServerType
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    visibleCustomerFields: List<CustomerField>,
    additionalFields: List<AdditionalField> = emptyList(),
    customerFieldValues: List<CustomerFieldValue> = emptyList(),
    onCustomerFieldsChanged: (List<CustomerFieldValue>, Boolean) -> Unit,
) {

    val visibleRequiredCustomerFields = remember { visibleCustomerFields.filter { it.isRequired } }
    val changedFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }
    val changedNonRequiredFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }

    val validate: (CustomerField, String, Boolean) -> Unit = { customerField, value, isValid ->
        validateFields(
            customerField = customerField,
            value = value,
            isValid = isValid,
            changedFieldsMap = changedFieldsMap,
            changedNonRequiredFieldsMap = changedNonRequiredFieldsMap,
            visibleRequiredFields = visibleRequiredCustomerFields,
            onCustomerFieldsChanged = onCustomerFieldsChanged
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        visibleCustomerFields.forEachIndexed { index, field ->
            if (index < visibleCustomerFields.size)
                Spacer(modifier = Modifier.size(10.dp))
            val foundAdditionalField =
                additionalFields.firstOrNull { it.type == field.type }
            val foundCustomerFieldValue =
                customerFieldValues.firstOrNull { it.name == field.name }
            when (field.serverType) {
                FieldServerType.TEL -> {
                    TelCustomerTextField(
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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
                        value = foundCustomerFieldValue?.value ?: foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validate(
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

private fun validateFields(
    customerField: CustomerField,
    value: String,
    isValid: Boolean,
    changedFieldsMap: MutableMap<String, CustomerFieldValue>,
    changedNonRequiredFieldsMap: MutableMap<String, CustomerFieldValue>,
    visibleRequiredFields: List<CustomerField>,
    onCustomerFieldsChanged: (List<CustomerFieldValue>, Boolean) -> Unit
) {
    //добавляем в мапу поля, которые были изменены пользователем
    //проверка на валидность и обязательность
    if (isValid && customerField.isRequired) {
        changedFieldsMap[customerField.name] =
            CustomerFieldValue.fromNameWithValue(customerField.name, value)
    } else if (!customerField.isRequired) {
        //добавляем в мапу измененные необязательное поля
        changedNonRequiredFieldsMap[customerField.name] =
            CustomerFieldValue.fromNameWithValue(customerField.name, value)
    } else if (customerField.isRequired) {
        changedFieldsMap.remove(customerField.name)
    }
    //список всех обязательных полей (по имени)
    val allRequiredFields = visibleRequiredFields.map { it.name }.sorted().toTypedArray()
    //список всех измененных обязательных полей (по имени)
    val changedRequiredCustomerFieldsList = changedFieldsMap.keys.sorted().toTypedArray()
    val allCustomerFields = (changedFieldsMap + changedNonRequiredFieldsMap).values.map {
        CustomerFieldValue.fromNameWithValue(
            it.name,
            it.value
        )
    }
    onCustomerFieldsChanged(
        allCustomerFields,
        allRequiredFields contentEquals changedRequiredCustomerFieldsList  //проверка, что список всех обязательных полей соответствует списку измененных и прошедших проверку обязательных полей
    )
}