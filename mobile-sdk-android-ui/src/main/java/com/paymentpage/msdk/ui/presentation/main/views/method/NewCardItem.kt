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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.merge
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
    var customerFieldValues by remember {
        mutableStateOf<List<CustomerFieldValue>?>(null)
    }
    val savedState = remember { mutableStateOf(false) }
    var isCustomerFieldsValid by remember { mutableStateOf(false) }

    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                modifier = Modifier.fillMaxWidth(),
                cardTypes = method.paymentMethod.cardTypes ?: emptyList(),
                onValueChanged = { value, isValid ->

                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            CardHolderField(modifier = Modifier.fillMaxWidth(),
                onValueChanged = { value, isValid ->

                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueChanged = { value, isValid ->

                    }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    modifier = Modifier.weight(1f),
                    onValueChanged = { value, isValid ->

                    }
                )
            }
            if (visibleCustomerFields.isNotEmpty()) {
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CustomerFields(
                    visibleCustomerFields = visibleCustomerFields,
                    additionalFields = additionalFields,
                    onCustomerFieldsChanged = { fields, isValid ->
                        customerFieldValues = fields
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
                    onCheckedChange = { savedState.value = it },
                    colors = CheckboxDefaults.colors(checkedColor = SDKTheme.colors.brand)
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding12))
                Text(
                    stringResource(R.string.save_card_label),
                    color = SDKTheme.colors.primaryTextColor,
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                isEnabled = (isCustomerFieldsValid || visibleCustomerFields.isEmpty()),
                onClick = {
                    method.cvv = ""
                    method.pan = ""
                    method.year = 0
                    method.month = 0
                    method.cardHolder = ""
                    method.saveCard = savedState.value
                    viewModel.saleCard(
                        method = method,
                        customerFields = customerFields.merge(
                            changedFields = customerFieldValues,
                            additionalFields = additionalFields
                        )
                    )
                }
            )
        }
    }
}