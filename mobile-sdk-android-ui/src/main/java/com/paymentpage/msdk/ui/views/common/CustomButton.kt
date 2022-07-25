package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.PayButton

@Composable
internal fun CustomButton(
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit),
    isEnabled: Boolean,
    onClick: () -> Unit,
    color: Color = SDKTheme.colors.brand
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(SDKTheme.colors.backgroundColor)
    ) {
        Button(
            onClick = onClick,
            content = {
                content()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color,
                disabledBackgroundColor = color.copy(alpha = 0.4f)
            ),
            shape = SDKTheme.shapes.radius6,
            modifier = modifier
                .height(LocalDimensions.current.buttonHeight)
                .fillMaxWidth(),
            enabled = isEnabled,
        )
    }
}

@Composable
@Preview
private fun PayButtonDefaultPreview() {
    SDKTheme {
        PayButton(
            payLabel = "Pay",
            amount = "100.00",
            currency = "USD",
            isEnabled = true,
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun PayButtonDisabledPreview() {
    SDKTheme {
        PayButton(
            payLabel = "Pay",
            amount = "100.00",
            currency = "USD",
            isEnabled = false,
            onClick = {}
        )
    }
}