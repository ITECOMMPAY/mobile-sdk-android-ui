package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.pay.button.ButtonTheme
import com.google.pay.button.ButtonType
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.google.pay.button.PayButton
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.LocalDimensions

@Composable
internal fun GooglePayButton(
    allowedPaymentMethods: String,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    PayButton(
        allowedPaymentMethods = allowedPaymentMethods,
        onClick = onClick,
        enabled = enabled,
        type = ButtonType.Pay,
        modifier = Modifier
            .height(LocalDimensions.current.buttonHeight)
            .fillMaxWidth()
            .testTag(TestTagsConstants.GOOGLE_PAY_BUTTON),
        radius = 6.dp,
        theme = if (SDKTheme.colors.isDarkTheme) {
            ButtonTheme.Light
        } else {
            ButtonTheme.Dark
        }
    )
}