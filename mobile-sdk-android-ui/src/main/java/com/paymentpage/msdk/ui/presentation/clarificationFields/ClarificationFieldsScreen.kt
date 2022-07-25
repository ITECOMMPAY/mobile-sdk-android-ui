package com.paymentpage.msdk.ui.presentation.clarificationFields

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.sendClarificationFields
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun ClarificationFieldsScreen(
    onCancel: () -> Unit,
) {
    val viewModel = LocalMainViewModel.current
    val clarificationFields = viewModel.lastState.clarificationFields
    val method = viewModel.lastState.currentMethod
    var clarificationFieldValues by remember { mutableStateOf<List<ClarificationFieldValue>?>(null) }
    var isClarificationFieldsValid by remember { mutableStateOf(false) }
    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            CardView()
            Spacer(modifier = Modifier.size(15.dp))
            CustomerFields(
                visibleCustomerFields = clarificationFields.map {
                    CustomerField(
                        name = it.name,
                        validatorName = it.validatorName,
                        validator = it.validator,
                        placeholder = it.defaultPlaceholder,
                        hint = it.defaultHint,
                        label = it.defaultLabel ?: "",
                        errorMessage = it.defaultErrorMessage,
                        errorMessageKey = "message_general_invalid",
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
            Spacer(modifier = Modifier.size(22.dp))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isClarificationFieldsValid
            ) {
                viewModel.sendClarificationFields(clarificationFieldValues!!)
            }

        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = { onCancel() },
    )
}
