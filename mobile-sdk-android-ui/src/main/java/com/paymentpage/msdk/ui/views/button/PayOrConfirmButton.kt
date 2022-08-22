package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.*
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.isAllCustomerFieldsHidden
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields

@Composable
internal fun PayOrConfirmButton(
    method: UIPaymentMethod,
    customerFields: List<CustomerField>,
//    isValidCvv: Boolean = false,
//    isValidCustomerFields: Boolean = false,
//    isValidPan: Boolean = false,
//    isValidCardHolder: Boolean = false,
//    isValidExpiry: Boolean = false,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    onClickButton: () -> Unit,
) {
    val condition =
        customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS
    when {
        condition -> {
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isValid && isValidCustomerFields
            ) {
                onClickButton()
            }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
        else -> {
            ConfirmButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_confirmation"),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}