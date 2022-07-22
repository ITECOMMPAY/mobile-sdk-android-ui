package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleCard
import com.paymentpage.msdk.ui.presentation.main.views.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.views.button.ConfirmAndContinueButton
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.card.CardHolderField
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.card.PanField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun NewCardItem(
    method: UiPaymentMethod.UICardPayPaymentMethod,
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalAdditionalFields.current

    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }

    val savedState = remember { mutableStateOf(false) }
    var isCustomerFieldsValid by remember { mutableStateOf(false) }
    var isCvvValid by remember { mutableStateOf(false) }
    var isPanValid by remember { mutableStateOf(false) }
    var isCardHolderValid by remember { mutableStateOf(false) }
    var isExpiryValid by remember { mutableStateOf(false) }

    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                initialValue = method.pan,
                modifier = Modifier.fillMaxWidth(),
                cardTypes = method.paymentMethod.cardTypes,
                onValueChanged = { value, isValid ->
                    isPanValid = isValid
                    method.pan = value
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            CardHolderField(
                initialValue = method.cardHolder,
                modifier = Modifier.fillMaxWidth(),
                onValueChanged = { value, isValid ->
                    isCardHolderValid = isValid
                    method.cardHolder = value
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    initialValue = method.expiry,
                    onValueChanged = { value, isValid ->
                        isExpiryValid = isValid
                        method.expiry = value
                    }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    initialValue = method.cvv,
                    modifier = Modifier.weight(1f),
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
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
                        isCustomerFieldsValid = isValid
                    }
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        savedState.value = !savedState.value
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = savedState.value,
                    onCheckedChange = {
                        savedState.value = it
                        method.saveCard = it
                    },
                    colors = CheckboxDefaults.colors(checkedColor = SDKTheme.colors.brand)
                )
                Text(
                    PaymentActivity.stringResourceManager.getStringByKey("title_saved_cards"),
                    color = SDKTheme.colors.primaryTextColor,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            if (visibleCustomerFields.isNotEmpty() && visibleCustomerFields.size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                PayButton(
                    payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                    amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                    currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                    isEnabled = isCvvValid && isPanValid && isCardHolderValid && isExpiryValid && (isCustomerFieldsValid || visibleCustomerFields.isEmpty()),
                ) {
                    viewModel.saleCard(
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
                    viewModel.saleCard(method = method)
                }
            }
        }
    }
}