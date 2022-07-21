package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun ConfirmAndContinueButton(
    modifier: Modifier = Modifier,
    payLabel: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    CustomButton(
        modifier = modifier,
        isEnabled = isEnabled,
        content = {
            Text(
                text = payLabel,
                style = SDKTheme.typography.s16Normal.copy(color = Color.White)
            )
        },
        onClick = onClick
    )
}

@Composable
@Preview
private fun ConfirmAndContinueButtonDefaultPreview() {
    SDKTheme {
        ConfirmAndContinueButton(
            payLabel = "Confirm and continue",
            isEnabled = true
        ) {}
    }
}

@Composable
@Preview
private fun ConfirmAndContinueButtonDisabledPreview() {
    SDKTheme {
        ConfirmAndContinueButton(
            payLabel = "Confirm and continue",
            isEnabled = false
        ) {}
    }
}