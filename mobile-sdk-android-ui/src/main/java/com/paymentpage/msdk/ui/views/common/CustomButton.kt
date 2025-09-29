package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.LocalDimensions
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.PayButton

internal val circleButtonPadding = 4.dp

@Composable
internal fun CustomButton(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
    isEnabled: Boolean,
    shape: Shape = SDKTheme.shapes.radius64,
    secondaryColor: Color = SDKTheme.colors.secondary,
    primaryColor: Color = SDKTheme.colors.primary,
    isRightArrowVisible: Boolean = true,
    onClick: () -> Unit,
) {
    val alpha = when(isEnabled) {
        true -> 1.0f
        else -> 0.3f
    }

    Box {
        OutlinedButton(
            modifier = modifier
                .height(LocalDimensions.current.buttonHeight)
                .fillMaxWidth(),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = secondaryColor,
                disabledBackgroundColor = secondaryColor.copy(alpha = 0.3f)
            ),
            shape = shape,
            border = null,
            enabled = isEnabled,
            content = content
        )

        if (isRightArrowVisible) {
            Box(
                Modifier
                    .padding(circleButtonPadding)
                    .clip(SDKTheme.shapes.radius64)
                    .align(Alignment.BottomEnd)
                    .height(LocalDimensions.current.buttonHeight - circleButtonPadding * 2)
                    .background(primaryColor.copy(alpha = alpha)),
            ) {
                Image(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }
        }
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