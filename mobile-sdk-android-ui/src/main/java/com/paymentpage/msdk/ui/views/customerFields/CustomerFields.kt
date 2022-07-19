@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.views.customerFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.FieldServerType
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.customerFields.type.*

@Composable
internal fun CustomerFields(
    customerFields: List<CustomerField>,
    additionalFields: List<AdditionalField> = emptyList(),
    onCustomerFieldsSuccess: (List<CustomerFieldValue>?) -> Unit = {},
    onCustomerFieldsError: () -> Unit = {},
) {
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    val visibleRequiredCustomerFields = remember { visibleCustomerFields.filter { it.isRequired } }
    val changedRequiredCustomerFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }
    val changedNonRequiredCustomerFieldsMap = remember { mutableMapOf<String, CustomerFieldValue>() }
    Column(modifier = Modifier.fillMaxWidth()) {
        visibleCustomerFields.forEachIndexed { index, field ->
            if (index < customerFields.size - 1)
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            val foundAdditionalField =
                additionalFields.firstOrNull { it.type == field.type }
            when (field.serverType) {
                FieldServerType.TEL -> {
                    TelCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.DATE -> {
                    DateCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.NUMBER -> {
                    NumberCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.PASSWORD -> {
                    PasswordCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
                            )
                        },
                        customerField = field
                    )
                }
                FieldServerType.EMAIL -> {
                    EmailCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
                            )
                        },
                        customerField = field
                    )
                }
                else -> {
                    TextCustomerTextField(
                        value = foundAdditionalField?.value,
                        onValueChanged = { customerField, value, isValid ->
                            validateFields(
                                customerField = customerField,
                                customerFields = customerFields,
                                additionalFields = additionalFields,
                                value = value,
                                isValid = isValid,
                                changedRequiredCustomerFieldsMap = changedRequiredCustomerFieldsMap,
                                changedNonRequiredCustomerFieldsMap = changedNonRequiredCustomerFieldsMap,
                                visibleRequiredCustomerFields = visibleRequiredCustomerFields,
                                onCustomerFieldsSuccess = onCustomerFieldsSuccess,
                                onCustomerFieldsError = onCustomerFieldsError
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
    changedRequiredCustomerFieldsMap: MutableMap<String, CustomerFieldValue>,
    changedNonRequiredCustomerFieldsMap: MutableMap<String, CustomerFieldValue>,
    visibleRequiredCustomerFields: List<CustomerField>,
    onCustomerFieldsSuccess: (List<CustomerFieldValue>) -> Unit,
    onCustomerFieldsError: () -> Unit,
) {
    //добавляем в мапу поля, которые были изменены пользователем
    //проверка на валидность и обязательность
    if (isValid && customerField.isRequired) {
        changedRequiredCustomerFieldsMap[customerField.name] =
            CustomerFieldValue.fromNameWithValue(customerField.name, value)
    } else if (!customerField.isRequired) {
        //добавляем в мапу измененные необязательное поля
        changedNonRequiredCustomerFieldsMap[customerField.name] =
            CustomerFieldValue.fromNameWithValue(customerField.name, value)
    } else if (customerField.isRequired) {
        changedRequiredCustomerFieldsMap.remove(customerField.name)
    }
    //список всех обязательных полей (по имени)
    val allRequiredFields = visibleRequiredCustomerFields.map { it.name }.sorted().toTypedArray()
    //список всех измененных обязательных полей (по имени)
    val changedRequiredCustomerFieldsList = changedRequiredCustomerFieldsMap.keys.sorted().toTypedArray()
    //проверка, что список всех обязательных полей соответствует списку измененных и прошедших проверку обязательных полей
    if (allRequiredFields contentEquals changedRequiredCustomerFieldsList) {
        //сливаем все видимые поля (обязательные и не обязательные) в одну мапу
        val allVisibleCustomerFieldsMap = changedRequiredCustomerFieldsMap + changedNonRequiredCustomerFieldsMap
        //мапим объекты CustomerField в объекты CustomerFieldValue
        //1. Находим соответствующее additionalFieldValue
        //2. Если поле скрытое, то значение этому полю присваеваем из additionalFieldValue, если такое нашлось;
        //3. Если поле видимое, то находим его значение из allVisibleCustomerFieldsMap по ключу, если такое не нашлось
        // например, поле было предзаполнено, но пользователь с ним не взаимодействовал,
        // то значение этому полю присваеваем из additionalFieldValue, если такое нашлось;
        //4. Если fieldValue равно null, то value в CustomerFieldValue будет равно пустой строке
        val allCustomerFieldValues = customerFields.map { field ->
            val foundAdditionalFieldValue = additionalFields.find { field.type == it.type }?.value
            val fieldValue =
                if (field.isHidden)
                    foundAdditionalFieldValue
                else
                    allVisibleCustomerFieldsMap[field.name]?.value ?: foundAdditionalFieldValue
            CustomerFieldValue.fromNameWithValue(field.name, fieldValue ?: "")
        }
        onCustomerFieldsSuccess(allCustomerFieldValues)
    } else {
        onCustomerFieldsError()
    }
}