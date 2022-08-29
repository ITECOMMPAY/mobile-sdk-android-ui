package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields

@Composable
internal fun PayOrConfirmButton(
    method: UIPaymentMethod,
    customerFields: List<CustomerField>,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    onClickButton: () -> Unit,
) {
    val condition =
        customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS
    when {
        condition -> {
            PayButton(
                payLabel = getStringOverride("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isValid && isValidCustomerFields
            ) {
                onClickButton()
            }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            PayButton(
                payLabel = getStringOverride("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
        else -> {
            ConfirmButton(
                payLabel = getStringOverride("button_confirmation"),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}