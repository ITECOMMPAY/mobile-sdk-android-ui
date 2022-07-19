package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod

@Composable
internal fun PaymentMethodItem(
    isExpand: Boolean,
    method: UiPaymentMethod,
    onItemSelected: ((method: UiPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UiPaymentMethod) -> Unit),
) {
    when (method) {
        is UiPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
        is UiPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
        is UiPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
    }
}
