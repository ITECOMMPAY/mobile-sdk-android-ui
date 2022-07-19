package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.card.CardHolderField
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.card.PanField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.expandable.ExpandableItem

@Composable
internal fun NewCardItem(
    isExpand: Boolean,
    method: UiPaymentMethod.UICardPayPaymentMethod,
    onItemSelected: ((method: UiPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UiPaymentMethod) -> Unit)
) {
    val customerFields = remember { method.paymentMethod?.customerFields }
    val checkedState = remember { mutableStateOf(true) }
    ExpandableItem(
        index = method.index,
        name = method.title,
        iconUrl = method.paymentMethod?.iconUrl,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        onExpand = { onItemSelected(method) },
        onCollapse = { onItemUnSelected(method) },
        isExpanded = isExpand,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                modifier = Modifier.fillMaxWidth(),
                cardTypes = method.paymentMethod?.cardTypes ?: emptyList(),
                onValueEntered = {

                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            CardHolderField(modifier = Modifier.fillMaxWidth(), onValueChanged = {})
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueEntered = {

                    }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    modifier = Modifier.weight(1f),
                    onValueEntered = {

                    }
                )
            }
            if (!customerFields.isNullOrEmpty()) {
                CustomerFields(
                    customerFields = customerFields,
                    additionalFields = LocalAdditionalFields.current
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
                        checkedState.value = !checkedState.value
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
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
                isEnabled = true,
                onClick = {}
            )
        }
    }
}