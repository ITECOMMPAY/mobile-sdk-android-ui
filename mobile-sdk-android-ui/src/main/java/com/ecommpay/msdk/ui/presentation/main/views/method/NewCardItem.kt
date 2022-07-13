package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.expandable.ExpandableItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.button.PayButton
import com.ecommpay.msdk.ui.views.card.CardHolderField
import com.ecommpay.msdk.ui.views.card.CvvField
import com.ecommpay.msdk.ui.views.card.ExpiryField
import com.ecommpay.msdk.ui.views.card.PanField
import com.ecommpay.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun NewCardItem(
    isExpand: Boolean,
    method: UIPaymentMethod.UICardPayPaymentMethod,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    val customerFields = remember { method.paymentMethod?.customerFields }
    ExpandableItem(
        index = method.index,
        name = method.title,
        iconUrl = method.paymentMethod?.iconUrl,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        onExpand = { onItemSelected?.invoke(method) },
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
            CardHolderField(modifier = Modifier.fillMaxWidth(), onValueChange = {})
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
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CustomerFields(
                    customerFields = customerFields,
                    additionalFields = paymentOptions.additionalFields
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = paymentOptions.paymentInfo?.paymentCurrency?.uppercase() ?: "",
                isEnabled = true,
                onClick = {}
            )
        }
    }
}