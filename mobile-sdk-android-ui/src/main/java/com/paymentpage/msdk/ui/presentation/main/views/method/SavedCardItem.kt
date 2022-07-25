package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleSavedCard
import com.paymentpage.msdk.ui.presentation.main.views.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.views.button.ConfirmAndContinueButton
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun SavedCardItem(
    method: UiPaymentMethod.UISavedCardPayPaymentMethod,
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalAdditionalFields.current

    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }

    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCvvValid by remember { mutableStateOf( method.isValidCvv) }

    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    initialValue = method.savedAccount.cardExpiry?.stringValue ?: "",
                    isDisabled = true,
                    onValueChanged = { _, _ ->
                        //we can't change value and isValid always equals true
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                CvvField(
                    initialValue = method.cvv,
                    modifier = Modifier.weight(1f),
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
                        method.isValidCvv = isValid
                    }
                )
            }
            if (visibleCustomerFields.isNotEmpty() && visibleCustomerFields.size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                CustomerFields(
                    visibleCustomerFields = visibleCustomerFields,
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues,
                    onCustomerFieldsChanged = { fields, isValid ->
                        method.customerFieldValues = fields
                        method.isCustomerFieldsValid = isValid
                        isCustomerFieldsValid = isValid
                    }
                )
            }
            Spacer(modifier = Modifier.size(22.dp))
            if (visibleCustomerFields.isNotEmpty() && visibleCustomerFields.size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                PayButton(
                    payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                    amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                    currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                    isEnabled = isCvvValid && (isCustomerFieldsValid || visibleCustomerFields.isEmpty())
                ) {
                    viewModel.saleSavedCard(
                        method = method,
                        customerFields = customerFields.merge(
                            changedFields = method.customerFieldValues,
                            additionalFields = additionalFields
                        )
                    )
                }
            } else {
                ConfirmAndContinueButton(
                    payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_confirmation"),
                    isEnabled = isCvvValid
                ) {
                    viewModel.saleSavedCard(method = method)
                }
            }
        }
    }
}
