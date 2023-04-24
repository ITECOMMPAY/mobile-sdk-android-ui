package com.paymentpage.msdk.ui.views.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.*
import com.paymentpage.msdk.ui.views.recurring.RecurringAgreements

@Composable
internal fun CustomOrConfirmButton(
    actionType: SDKActionType,
    method: UIPaymentMethod,
    customerFields: List<CustomerField>,
    isValid: Boolean = false,
    isValidCustomerFields: Boolean = false,
    testTagPrefix: String = "",
    onClickButton: () -> Unit,
) {
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val condition =
        customerFields.hasVisibleCustomerFields() &&
                customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS
    when {
        condition -> {
            if (actionType == SDKActionType.Verify) {
                SDKButton(
                    modifier = Modifier
                        .testTag("$testTagPrefix${TestTagsConstants.AUTHORIZE_BUTTON}"),
                    label = getStringOverride(OverridesKeys.BUTTON_AUTHORIZE),
                    isEnabled = isValid && isValidCustomerFields
                ) {
                    onClickButton()
                }
                RecurringAgreements()
            }
            else
                PayButton(
                    modifier = Modifier
                        .testTag("$testTagPrefix${TestTagsConstants.PAY_BUTTON}"),
                    payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                    amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                    currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                    isEnabled = isValid && isValidCustomerFields
                ) {
                    onClickButton()
                }
        }
        customerFields.isAllCustomerFieldsHidden() -> {
            if (actionType == SDKActionType.Verify) {
                SDKButton(
                    modifier = Modifier
                        .testTag("$testTagPrefix${TestTagsConstants.AUTHORIZE_BUTTON}"),
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
                RecurringAgreements()
            }
            else
                PayButton(
                    modifier = Modifier
                        .testTag("$testTagPrefix${TestTagsConstants.PAY_BUTTON}"),
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
                modifier = Modifier
                    .testTag("$testTagPrefix${TestTagsConstants.CONFIRMATION_BUTTON}"),
                label = getStringOverride(OverridesKeys.BUTTON_CONFIRMATION),
                isEnabled = isValid
            ) {
                onClickButton()
            }
        }
    }
}