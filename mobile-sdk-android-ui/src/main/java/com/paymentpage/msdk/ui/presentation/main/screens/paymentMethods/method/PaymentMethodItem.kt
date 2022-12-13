package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    method: UIPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    when (method) {
        is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
            SavedCardItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
        is UIPaymentMethod.UICardPayPaymentMethod -> {
            NewCardItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
        is UIPaymentMethod.UITokenizeCardPayPaymentMethod -> {
            TokenizeCardPayItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
        is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            GooglePayItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
        is UIPaymentMethod.UIApsPaymentMethod -> {
            ApsPayItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
    }
}
