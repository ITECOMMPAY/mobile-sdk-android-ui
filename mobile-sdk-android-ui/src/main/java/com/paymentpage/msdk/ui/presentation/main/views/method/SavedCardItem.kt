package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
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
    method: UiPaymentMethod.UISavedCardPayPaymentMethod,
    onItemSelected: ((method: UiPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UiPaymentMethod) -> Unit),
) {
    val viewModel = LocalMainViewModel.current
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
        var cvv by remember { mutableStateOf("") }
        Column(Modifier.fillMaxWidth()) {
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    value = method.savedAccount.cardExpiry?.stringValue ?: "",
                    isDisabled = true,
                    onValueEntered = { cvv = it }
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                CvvField(
                    modifier = Modifier.weight(1f),
                    onValueEntered = { cvv = it }
                )
            }
            if (customerFields.isNotEmpty()) {
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
                //TODO need validate
                viewModel.saleSavedCard(
                    accountId = method.savedAccount.id,
                    cvv = cvv,
                    method = method
                )
            }
        }
    }
}
