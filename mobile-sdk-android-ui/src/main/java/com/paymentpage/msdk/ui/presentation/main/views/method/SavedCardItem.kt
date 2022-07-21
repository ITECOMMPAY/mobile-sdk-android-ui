package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.views.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.views.button.ConfirmAndContinueButton
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.expandable.ExpandableItem

@Composable
internal fun SavedCardItem(
    isExpand: Boolean,
    method: UiPaymentMethod.UISavedCardPayPaymentMethod,
    onItemSelected: ((method: UiPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UiPaymentMethod) -> Unit),
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalAdditionalFields.current

    var cvv by remember { mutableStateOf("") }
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    var customerFieldValues by remember {
        mutableStateOf<List<CustomerFieldValue>?>(null)
    }
    ExpandableItem(
        index = method.index,
        name = method.savedAccount.number,
        iconUrl = method.savedAccount.cardUrlLogo,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        onExpand = { onItemSelected(method) },
        onCollapse = { onItemUnSelected(method) },
        isExpanded = isExpand,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        Column(Modifier.fillMaxWidth()) {
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    value = method.savedAccount.cardExpiry?.stringValue ?: "",
                    isDisabled = true,
                    onValueChanged = { _, _ ->
                        //we can't change value and isValid always equals true
                    }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    initialValue = method.cvv,
                    modifier = Modifier.weight(1f),
                    onValueChanged = { value, isValid ->
                        cvv = if (isValid) value else ""
                    }
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            if (visibleCustomerFields.isNotEmpty() && visibleCustomerFields.size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                CustomerFields(
                    visibleCustomerFields = visibleCustomerFields,
                    additionalFields = additionalFields,
                    onCustomerFieldsSuccess = { customerFieldValues = it },
                    onCustomerFieldsError = { customerFieldValues = null }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
                PayButton(
                    payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                    amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                    currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                    isEnabled = cvv.isNotEmpty() && (customerFieldValues != null || visibleCustomerFields.isEmpty())
                ) {
                    method.cvv = cvv
                    viewModel.saleSavedCard(
                        method = method,
                        customerFields = customerFields.merge(
                            changedFields = customerFieldValues,
                            additionalFields = additionalFields
                        )
                    )
                }
            } else {
                ConfirmAndContinueButton(
                    payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_confirmation"),
                    isEnabled = cvv.isNotEmpty()
                ) {
                    method.cvv = cvv
                    viewModel.saleSavedCard(method = method)
                }
            }
        }
    }
}