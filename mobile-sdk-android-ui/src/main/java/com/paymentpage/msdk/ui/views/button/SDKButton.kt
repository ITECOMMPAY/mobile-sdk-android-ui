package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.theme.SDKColorButton
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
import com.paymentpage.msdk.ui.theme.defaults.SdkColorDefaults
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun SDKButton(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean,
    color: SDKColorButton = SdkColorDefaults.buttonColor(),
    textStyle: TextStyle = SDKTheme.typography.s14SemiBold.copy(
        color = color.text().value.copy(
            alpha = when {
                isEnabled -> 1.0f
                //disabled && darkTheme
                else -> 0.3f
            }
        ),
    ),
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        isEnabled = isEnabled,
        color = color,
        content = {
            Text(
                text = label,
                style = textStyle,
                fontFamily = SohneBreitFamily,
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