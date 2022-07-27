package com.paymentpage.msdk.ui.views.button

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton

@Composable
internal fun PayButton(
    modifier: Modifier = Modifier,
    payLabel: String,
    amount: String,
    currency: String,
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
            Text(text = " ")
            Text(
                text = amount,
                style = SDKTheme.typography.s16Normal.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = " ")
            Text(
                text = currency,
                style = SDKTheme.typography.s16Normal.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        onClick = onClick
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
            isEnabled = true
        ) {
        }
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
            isEnabled = false
        ) {
        }
    }
}