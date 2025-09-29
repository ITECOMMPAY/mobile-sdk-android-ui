package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun SDKButton(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean,
    textStyle: TextStyle = SDKTheme.typography.s16Normal.copy(
        color = Color.Black.copy(
            alpha = when {
                isEnabled -> 1.0f
                //disabled && darkTheme
                else -> 0.3f
            }
        ),
    ),
    color: Color = SDKTheme.colors.secondary,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        isEnabled = isEnabled,
        secondaryColor = color,
        content = {
            Text(
                text = label,
                style = textStyle
            )
        },
        onClick = onClick
    )
}

@Composable
@Preview
private fun ConfirmButtonDefaultPreview() {
    SDKTheme {
        SDKButton(
            label = "Some text",
            isEnabled = true
        ) {}
    }
}

@Composable
@Preview
private fun ConfirmAButtonDisabledPreview() {
    SDKTheme {
        SDKButton(
            label = "Some text",
            isEnabled = false
        ) {}
    }
}