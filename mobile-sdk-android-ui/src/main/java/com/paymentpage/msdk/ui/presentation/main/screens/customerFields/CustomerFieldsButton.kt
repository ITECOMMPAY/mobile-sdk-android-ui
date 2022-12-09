package com.paymentpage.msdk.ui.presentation.main.screens.customerFields

import androidx.compose.runtime.Composable
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.PayButton
import com.paymentpage.msdk.ui.views.button.SDKButton


@Composable
internal fun CustomerFieldsButton(
    actionType: SDKActionType,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    when (actionType) {
        SDKActionType.Sale -> {
            PayButton(
                payLabel = getStringOverride(OverridesKeys.BUTTON_PAY),
                amount = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                currency = LocalPaymentOptions.current.paymentInfo.paymentCurrency.uppercase(),
                isEnabled = isEnabled,
                onClick = onClick
            )
        }
        SDKActionType.Tokenize -> {
            SDKButton(
                label = getStringOverride(OverridesKeys.BUTTON_PROCEED),
                isEnabled = isEnabled,
                onClick = onClick
            )
        }
        else -> {
            SDKButton(
                label = getStringOverride(OverridesKeys.BUTTON_PROCEED),
                isEnabled = isEnabled,
                onClick = onClick
            )
        }
    }
}