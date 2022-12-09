package com.paymentpage.msdk.ui.presentation.main.screens.clarificationFields

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.sendClarificationFields
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun ClarificationFieldsScreen(
    onCancel: () -> Unit,
) {
    val viewModel = LocalMainViewModel.current
    val clarificationFields = viewModel.lastState.clarificationFields
    var clarificationFieldValues by remember { mutableStateOf<List<ClarificationFieldValue>?>(null) }
    var isClarificationFieldsValid by remember { mutableStateOf(false) }

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = getStringOverride(OverridesKeys.TITLE_PAYMENT_ADDITIONAL_DATA),
        scrollableContent = {
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            Text(
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
                        isRequired = true //clarification fields always are true
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
            PayButton(
                payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isClarificationFieldsValid
            ) {
                viewModel.sendClarificationFields(clarificationFieldValues!!)
            }
            Spacer(modifier = Modifier.size(16.dp))
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = { onCancel() },
    )
}
