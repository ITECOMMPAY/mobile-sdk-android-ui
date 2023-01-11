package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.PayButton

@Composable
internal fun CustomButton(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
    isEnabled: Boolean,
    shape: Shape = SDKTheme.shapes.radius6,
    color: Color = SDKTheme.colors.brand,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .height(LocalDimensions.current.buttonHeight)
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            disabledBackgroundColor = color.copy(alpha = 0.4f)
        ),
        shape = shape,
        border = null,
        enabled = isEnabled,
        content = content
    )
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