package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    method: UIPaymentMethod,
    actionType: SDKActionType,
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
            if (actionType != SDKActionType.Tokenize)
                NewCardItem(
                    method = method,
                    actionType = actionType,
                    isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
                )
            else
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
            Spacer(modifier = Modifier.size(6.dp))
        }

        is UIPaymentMethod.UIApsPaymentMethod -> {
            ApsPayItem(
                method = method,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
        }
    }
}
