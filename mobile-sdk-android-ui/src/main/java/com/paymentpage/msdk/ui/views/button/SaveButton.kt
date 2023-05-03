package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.mergeHiddenFieldsToList
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields

@Composable
internal fun SaveButton(
    modifier: Modifier = Modifier,
    method: UIPaymentMethod,
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
                modifier = modifier,
                label = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
                isEnabled = isValid && isValidCustomerFields
            ) {
                onClickButton()
            }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            SDKButton(
                modifier = modifier,
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
                modifier = modifier,
                label = getStringOverride(OverridesKeys.BUTTON_TOKENIZE),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}