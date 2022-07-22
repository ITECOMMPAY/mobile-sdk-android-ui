package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod

@Composable
internal fun PaymentMethodItem(
    method: UiPaymentMethod
) {
    when (method) {
        is UiPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(method = method)
        }
        is UiPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(method = method)
        }
        is UiPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(method = method)
        }
    }
}
