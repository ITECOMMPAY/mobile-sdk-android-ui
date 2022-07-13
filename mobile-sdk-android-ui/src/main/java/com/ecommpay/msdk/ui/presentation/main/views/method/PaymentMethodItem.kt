package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.runtime.Composable
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    isExpand: Boolean,
    method: UIPaymentMethod,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    when (method) {
        is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(
                isExpand = isExpand,
                paymentOptions = paymentOptions,
                method = method
            ) { onItemSelected?.invoke(it) }
        }
        is UIPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(
                isExpand = isExpand,
                paymentOptions = paymentOptions,
                method = method,
            ) { onItemSelected?.invoke(it) }
        }
        is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(
                isExpand = isExpand,
                paymentOptions = paymentOptions,
                method = method
            ) { onItemSelected?.invoke(it) }
        }
    }
}
