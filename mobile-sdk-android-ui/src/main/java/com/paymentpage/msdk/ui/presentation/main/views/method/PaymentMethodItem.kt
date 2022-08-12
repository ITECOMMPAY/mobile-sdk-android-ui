package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    method: UIPaymentMethod
) {
    when (method) {
        is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(method = method)
        }
        is UIPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(method = method)
        }
        is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(method = method)
        }
        is UIPaymentMethod.UIApsPaymentMethod -> {
            ApsPayItem(method = method)
        }
    }
}
