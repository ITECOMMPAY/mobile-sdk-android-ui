package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields

@Composable
internal fun SaveButton(
    method: UIPaymentMethod.UITokenizeCardPayPaymentMethod,
    customerFields: List<CustomerField>,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    onClickButton: () -> Unit,
) {
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