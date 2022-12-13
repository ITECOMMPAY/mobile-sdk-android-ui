package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.*

@Composable
internal fun SaveButton(
    method: UIPaymentMethod.UITokenizeCardPayPaymentMethod,
    customerFields: List<CustomerField>,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    onClickButton: () -> Unit,
) {
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val condition =
        customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
    when {
        condition -> {
            SDKButton(
                label = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
                isEnabled = isValid && isValidCustomerFields
            ) {
                onClickButton()
            }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            SDKButton(
                label = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
                isEnabled = isValid
            ) {
                method.customerFieldValues = customerFields.mergeHiddenFieldsToList(
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues
                ).map {
                    CustomerFieldValue(
                        name = it.name,
                        value = it.value
                    )
                }
                onClickButton()
            }
        }
        else -> {
            SDKButton(
                label = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}