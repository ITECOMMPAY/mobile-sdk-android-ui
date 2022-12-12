package com.paymentpage.msdk.ui.views.customerFields

import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.FieldServerType
import com.paymentpage.msdk.core.validators.custom.EmailValidator
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.SDKAdditionalFieldType
import com.paymentpage.msdk.ui.utils.extensions.core.*
import com.paymentpage.msdk.ui.views.customerFields.model.UICustomerFieldValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomerFieldsTest {
    @Test
    fun `hasVisibleCustomerFields must return true`() {
        //GIVEN
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                label = "Email",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                isHidden = true,
                label = "First name",
                errorMessageKey = "message_general_invalid"
            )
        )

        //WHEN
        val result = customerFieldsList.hasVisibleCustomerFields()

        //THEN
        assertTrue(result)
    }

    @Test
    fun `isAllCustomerFieldsHidden must return true`() {
        //GIVEN
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                label = "Email",
                isHidden = true,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                isHidden = true,
                label = "First name",
                errorMessageKey = "message_general_invalid"
            )
        )

        //WHEN
        val result = customerFieldsList.isAllCustomerFieldsHidden()

        //THEN
        assertTrue(result)
    }

    @Test
    fun `visibleCustomerFields must return only visible customer fields`() {
        //GIVEN
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                label = "Email",
                isHidden = true,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        )

        val visibleCustomerFields = listOf(
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        )

        //WHEN
        val result = customerFieldsList.visibleCustomerFields()

        //THEN
        assertTrue(result.isEqual(visibleCustomerFields))
    }

    @Test
    fun `hiddenCustomerFields must return only hidden customer fields`() {
        //GIVEN
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                isHidden = true,
                label = "Email",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        )

        val hiddenCustomerFields = listOf(
            CustomerField(
                name = "email",
                isHidden = true,
                label = "Email",
                errorMessageKey = "message_general_invalid"
            )
        )

        //WHEN
        val result = customerFieldsList.hiddenCustomerFields()

        //THEN
        assertTrue(result.isEqual(hiddenCustomerFields))
    }

    @Test
    fun `Visible required fields with validator and correct data`() {
        //GIVEN
        val emailValidator = EmailValidator()
        val additionalFields = listOf(
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_EMAIL,
                value = "pp@pp.pp"
            ),
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_FIRST_NAME,
                value = "Billy"
            )
        )
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                isRequired = true,
                label = "Email",
                validator = emailValidator,
                serverType = FieldServerType.EMAIL,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                isRequired = true,
                label = "First name",
                serverType = FieldServerType.TEXT,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                isHidden = true,
                label = "Last name",
                serverType = FieldServerType.TEXT,
                errorMessageKey = "message_general_invalid"
            )
        )

        val customerFieldValues = emptyList<CustomerFieldValue>()

        //WHEN
        val result = customerFieldsList.mergeVisibleFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        )

        val visibleUICustomerFieldValues = listOf(
            UICustomerFieldValue(
                name = "email",
                value = "pp@pp.pp",
                isValid = true,
                isRequired = true,
                isHidden = false
            ),
            UICustomerFieldValue(
                name = "first_name",
                value = "Billy",
                isValid = true,
                isRequired = true,
                isHidden = false
            )
        )

        //THEN
        assertEquals(result, visibleUICustomerFieldValues)
    }

    @Test
    fun `Visible required fields with validator and incorrect data`() {
        //GIVEN
        val emailValidator = EmailValidator()
        val additionalFields = listOf(
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_FIRST_NAME,
                value = "Billy"
            )
        )
        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                isRequired = true,
                label = "Email",
                validator = emailValidator,
                serverType = FieldServerType.EMAIL,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                label = "First name",
                serverType = FieldServerType.TEXT,
                errorMessageKey = "message_general_invalid"
            )
        )

        val customerFieldValues = listOf(
            CustomerFieldValue(
                name = "email",
                value = "pp@pp"
            ),
            CustomerFieldValue(
                name = "first_name",
                value = "Billy"
            )
        )

        //WHEN
        val result = customerFieldsList.mergeVisibleFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        )

        val visibleUICustomerFieldValueList = listOf(
            UICustomerFieldValue(
                name = "email",
                value = "pp@pp",
                isValid = false,
                isRequired = true,
                isHidden = false
            ),
            UICustomerFieldValue(
                name = "first_name",
                value = "Billy",
                isValid = true,
                isRequired = false,
                isHidden = false
            )
        )

        //THEN
        assertEquals(result, visibleUICustomerFieldValueList)
    }

    @Test
    fun `Hidden pre-filled fields for sale`() {
        //GIVEN
        val emailValidator = EmailValidator()

        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                isHidden = true,
                label = "Email",
                validator = emailValidator,
                serverType = FieldServerType.EMAIL,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                isHidden = true,
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        )

        val additionalFields = listOf(
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_EMAIL,
                value = "pp@pp.pp"
            ),
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_LAST_NAME,
                value = "Jordan"
            )
        )

        val customerFieldValues = listOf(
            CustomerFieldValue(
                name = "last_name",
                value = "Jordan"
            )
        )

        //WHEN
        val resultCustomerFieldsToSend = customerFieldsList.mergeVisibleFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        ) + customerFieldsList.mergeHiddenFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        )

        val customerFieldsToSend = listOf(
            UICustomerFieldValue(
                name = "last_name",
                value = "Jordan",
                isRequired = false,
                isValid = true,
                isHidden = false
            ),
            UICustomerFieldValue(
                name = "email",
                value = "pp@pp.pp",
                isRequired = false,
                isValid = true,
                isHidden = true
            ),
            UICustomerFieldValue(
                name = "first_name",
                value = "",
                isRequired = false,
                isValid = true,
                isHidden = true
            )
        )

        //THEN
        assertEquals(resultCustomerFieldsToSend, customerFieldsToSend)
    }

    @Test
    fun `Hidden pre-filled fields for tokenization`() {
        //GIVEN
        val emailValidator = EmailValidator()

        val customerFieldsList = listOf(
            CustomerField(
                name = "email",
                isHidden = true,
                isTokenize = true,
                label = "Email",
                validator = emailValidator,
                serverType = FieldServerType.EMAIL,
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "first_name",
                isHidden = true,
                label = "First name",
                errorMessageKey = "message_general_invalid"
            ),
            CustomerField(
                name = "last_name",
                isTokenize = true,
                label = "Last name",
                errorMessageKey = "message_general_invalid"
            )
        ).filter { it.isTokenize }

        val additionalFields = listOf(
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_EMAIL,
                value = "pp@pp.pp"
            ),
            SDKAdditionalField(
                type = SDKAdditionalFieldType.CUSTOMER_LAST_NAME,
                value = "Jordan"
            )
        )

        val customerFieldValues = listOf(
            CustomerFieldValue(
                name = "last_name",
                value = "Jordan"
            )
        )

        //WHEN
        val resultCustomerFieldsToSend = customerFieldsList.mergeVisibleFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        ) + customerFieldsList.mergeHiddenFieldsToList(
            customerFieldValues = customerFieldValues,
            additionalFields = additionalFields
        )

        val customerFieldsToSend = listOf(
            UICustomerFieldValue(
                name = "last_name",
                value = "Jordan",
                isRequired = false,
                isValid = true,
                isHidden = false
            ),
            UICustomerFieldValue(
                name = "email",
                value = "pp@pp.pp",
                isRequired = false,
                isValid = true,
                isHidden = true
            ),
        )

        //THEN
        assertEquals(resultCustomerFieldsToSend, customerFieldsToSend)
    }
}

private fun CustomerField.isEqual(customerField: CustomerField): Boolean =
    when {
        this == customerField -> true
        this.name == customerField.name &&
                this.errorMessageKey == customerField.errorMessageKey &&
                this.label == customerField.label -> true
        else -> false
    }

private fun List<CustomerField>.isEqual(customerFields: List<CustomerField>): Boolean =
    when {
        this == customerFields -> true
        this.size == customerFields.size -> {
            var result = true
            this.forEachIndexed { index, customerField ->
                if (!customerField.isEqual(customerFields[index])) {
                    result = false
                }
            }
            result
        }
        else -> false
    }