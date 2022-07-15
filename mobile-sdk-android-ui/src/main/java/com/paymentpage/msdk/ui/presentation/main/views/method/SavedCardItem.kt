package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.expandable.ExpandableItem

@Composable
internal fun SavedCardItem(
    isExpand: Boolean,
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
    onItemSelected: ((method: UIPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UIPaymentMethod) -> Unit),
) {
    val customerFields = remember { method.paymentMethod?.customerFields }
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
                    onValueEntered = {}
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    modifier = Modifier.weight(1f),
                    onValueEntered = {}
                )
            }
            if (!customerFields.isNullOrEmpty()) {
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CustomerFields(
                    customerFields = customerFields,
                    additionalFields = LocalAdditionalFields.current
                )
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                isEnabled = true
            ) {

            }
        }
    }
}
