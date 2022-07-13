package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.expandable.ExpandableItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.button.PayButton
import com.ecommpay.msdk.ui.views.card.CardHolderField
import com.ecommpay.msdk.ui.views.card.CvvField
import com.ecommpay.msdk.ui.views.card.ExpiryField
import com.ecommpay.msdk.ui.views.card.PanField

@Composable
internal fun NewCardItem(
    isExpand: Boolean,
    method: UIPaymentMethod.UICardPayPaymentMethod,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    ExpandableItem(
        index = method.index,
        name = method.title,
        iconUrl = method.paymentMethod?.iconUrl,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        onExpand = { onItemSelected?.invoke(method) },
        isExpanded = isExpand,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                modifier = Modifier.fillMaxWidth(),
                cardTypes = method.paymentMethod?.cardTypes ?: emptyList(),
                onValueChange = {}
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp12))
            CardHolderField(modifier = Modifier.fillMaxWidth(), onValueChange = {})
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp12))
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueEntered = {

                    }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
                CvvField(
                    modifier = Modifier.weight(1f),
                    onCvvEntered = {}
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp22))
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