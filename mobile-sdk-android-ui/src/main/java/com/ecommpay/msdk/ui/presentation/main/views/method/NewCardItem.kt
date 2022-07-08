package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentActivity
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
    paymentMethod: UIPaymentMethod.UICardPayPaymentMethod,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    ExpandableItem(
        index = paymentMethod.index,
        name = paymentMethod.title,
        iconUrl = paymentMethod.paymentMethod?.iconUrl,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        onExpand = { onItemSelected?.invoke(paymentMethod) },
        isExpanded = isExpand
    ) {
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                modifier = Modifier.fillMaxWidth(),
                cardTypes = paymentMethod.paymentMethod?.cardTypes ?: emptyList(),
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
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay")
                    ?: stringResource(R.string.button_pay_title),
                amount = PaymentActivity.paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = PaymentActivity.paymentOptions.paymentInfo?.paymentCurrency?.uppercase()
                    ?: "",
                isEnabled = true,
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SavedCardItemPreview() {
    SavedCardItem(
        isExpand = true,
        paymentMethod = UIPaymentMethod.UISavedCardPayPaymentMethod(
            index = 0,
            title = "**** 3456",
            savedAccount = SavedAccount(
                id = 0,
                number = "**** 3456",
                cardExpiry = null,
                cardUrlLogo = "https://pp-sdk.westresscode.net/card_icons/mastercard.png"
            ),
            paymentMethod = null
        )
    )
}
