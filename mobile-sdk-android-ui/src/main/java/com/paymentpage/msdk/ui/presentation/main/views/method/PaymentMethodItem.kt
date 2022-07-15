package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    isExpand: Boolean,
    method: UIPaymentMethod,
    onItemSelected: ((method: UIPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UIPaymentMethod) -> Unit),
) {
    when (method) {
        is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
        is UIPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
        is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(
                isExpand = isExpand,
                method = method,
                onItemSelected = onItemSelected,
                onItemUnSelected = onItemUnSelected
            )
        }
    }
}
