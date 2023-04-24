package com.paymentpage.msdk.ui.presentation.main.screens.clarificationFields

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.presentation.main.sendClarificationFields
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.button.SDKButton
import com.paymentpage.msdk.ui.views.common.ExpandablePaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.recurring.RecurringAgreements

@Composable
internal fun ClarificationFieldsScreen(
    actionType: SDKActionType,
    onCancel: () -> Unit,
) {
    val viewModel = LocalMainViewModel.current
    val clarificationFields = viewModel.lastState.clarificationFields
    var clarificationFieldValues by remember {
        mutableStateOf<List<ClarificationFieldValue>>(emptyList())
    }
    var isClarificationFieldsValid by remember { mutableStateOf(false) }

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = getStringOverride(OverridesKeys.TITLE_PAYMENT_ADDITIONAL_DATA),
        scrollableContent = {
            ExpandablePaymentOverview(
                actionType = actionType,
                expandable = true
            )
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                modifier = Modifier
                    .testTag(TestTagsConstants.ADDITIONAL_DATA_DISCLAIMER_TEXT),
                text = getStringOverride(OverridesKeys.TITLE_PAYMENT_ADDITIONAL_DATA_DISCLAIMER),
                style = SDKTheme.typography.s14Normal
            )
            Spacer(modifier = Modifier.size(5.dp))
            CustomerFields(
                customerFields = clarificationFields.map {
                    CustomerField(
                        name = it.name,
                        validatorName = it.validatorName,
                        validator = it.validator,
                        placeholder = it.defaultPlaceholder,
                        hint = it.defaultHint,
                        label = it.defaultLabel ?: "",
                        errorMessage = it.defaultErrorMessage,
                        errorMessageKey = OverridesKeys.MESSAGE_GENERAL_INVALID,
                        isRequired = true //clarification fields always are require
                    )
                },
                onCustomerFieldsChanged = { fields, isValid ->
                    clarificationFieldValues = fields.map {
                        ClarificationFieldValue(
                            name = it.name,
                            value = it.value
                        )
                    }
                    isClarificationFieldsValid = isValid
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            if (actionType != SDKActionType.Verify)
                PayButton(
                    modifier = Modifier
                        .testTag(TestTagsConstants.PAY_BUTTON),
                    payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                    amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                    currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                    isEnabled = isClarificationFieldsValid
                ) {
                    viewModel.sendClarificationFields(clarificationFieldValues)
                }
            else {
                SDKButton(
                    modifier = Modifier
                        .testTag(TestTagsConstants.AUTHORIZE_BUTTON),
                    label = getStringOverride(OverridesKeys.BUTTON_AUTHORIZE),
                    isEnabled = isClarificationFieldsValid
                ) {
                    viewModel.sendClarificationFields(clarificationFieldValues)
                }
                RecurringAgreements()
            }
            Spacer(modifier = Modifier.size(16.dp))
            SDKFooter()
        },
        onClose = { onCancel() },
    )
}
