package com.paymentpage.msdk.ui.views.button

import com.paymentpage.msdk.ui.SDKActionType

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.*

@Composable
internal fun CustomOrConfirmButton(
    actionType: SDKActionType,
    method: UIPaymentMethod,
    customerFields: List<CustomerField>,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    onClickButton: () -> Unit,
) {
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val condition =
        customerFields.hasVisibleCustomerFields() &&
                customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS
    when {
        condition -> {
            if (actionType == SDKActionType.Verify)
                SDKButton(
                    label = getStringOverride(OverridesKeys.BUTTON_AUTHORIZE),
                    isEnabled = isValid && isValidCustomerFields
                ) {
                    onClickButton()
                }
            else
                PayButton(
                    payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                    amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                    currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                    isEnabled = isValid && isValidCustomerFields
                ) {
                    onClickButton()
                }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            if (actionType == SDKActionType.Verify)
                SDKButton(
                    label = getStringOverride(OverridesKeys.BUTTON_AUTHORIZE),
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
            else
                PayButton(
                    payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                    amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                    currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
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
                label = getStringOverride(OverridesKeys.BUTTON_CONFIRMATION),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}