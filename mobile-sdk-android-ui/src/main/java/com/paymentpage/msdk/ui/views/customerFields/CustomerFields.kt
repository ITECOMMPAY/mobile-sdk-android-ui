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
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    visibleCustomerFields: List<CustomerField>,
    additionalFields: List<AdditionalField> = emptyList(),
    onCustomerFieldsSuccess: (List<CustomerFieldValue>?) -> Unit = {},
    onCustomerFieldsError: () -> Unit = {},
) {

    val visibleRequiredCustomerFields = remember { visibleCustomerFields.filter { it.isRequired } }
    val changedFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }
    val changedNonRequiredFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }

    val validate: (CustomerField, String, Boolean) -> Unit = { customerField, value, isValid ->
        validateFields(
            customerField = customerField,
            customerFields = visibleCustomerFields,
            additionalFields = additionalFields,
            value = value,
            isValid = isValid,
            changedFieldsMap = changedFieldsMap,
            changedNonRequiredFieldsMap = changedNonRequiredFieldsMap,
            visibleRequiredFields = visibleRequiredCustomerFields,
            onCustomerFieldsSuccess = onCustomerFieldsSuccess,
            onCustomerFieldsError = onCustomerFieldsError
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        visibleCustomerFields.forEachIndexed { index, field ->
            if (index < visibleCustomerFields.size)
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            val foundAdditionalField =
                additionalFields.firstOrNull { it.type == field.type }
            when (field.serverType) {
                FieldServerType.TEL -> {
                    TelCustomerTextField(
                        value = foundAdditionalField?.value,
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
                        value = foundAdditionalField?.value,
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
                        value = foundAdditionalField?.value,
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
                        value = foundAdditionalField?.value,
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
                        value = foundAdditionalField?.value,
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
                        value = foundAdditionalField?.value,
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
    customerFields: List<CustomerField>,
    additionalFields: List<AdditionalField>,
    value: String,
    isValid: Boolean,
    changedFieldsMap: MutableMap<String, CustomerFieldValue>,
    changedNonRequiredFieldsMap: MutableMap<String, CustomerFieldValue>,
    visibleRequiredFields: List<CustomerField>,
    onCustomerFieldsSuccess: (List<CustomerFieldValue>) -> Unit,
    onCustomerFieldsError: () -> Unit,
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
        changedNonRequiredFieldsMap.remove(customerField.name)
    }

    //список всех обязательных полей (по имени)
    val allRequiredFields = visibleRequiredFields.map { it.name }.toTypedArray()
    //список всех измененных обязательных полей (по имени)
    val changedRequiredCustomerFieldsList = changedFieldsMap.keys.toTypedArray()
    //проверка, что список всех обязательных полей соответствует списку измененных и прошедших проверку обязательных полей
    if (allRequiredFields contentEquals changedRequiredCustomerFieldsList) {
        //сливаем все видимые поля (обязательные и не обязательные) в одну мапу
        val allCustomerFieldsMap = changedFieldsMap + changedNonRequiredFieldsMap
        onCustomerFieldsSuccess(allCustomerFieldsMap.values.map {
            CustomerFieldValue.fromNameWithValue(
                it.name,
                it.value
            )
        })
    } else {
        onCustomerFieldsError()
    }
}