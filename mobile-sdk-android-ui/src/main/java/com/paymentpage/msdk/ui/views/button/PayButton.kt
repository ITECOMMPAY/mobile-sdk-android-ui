package com.paymentpage.msdk.ui.views.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun PayButton(
    modifier: Modifier = Modifier,
    payLabel: String,
    amount: String,
    currency: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(SDKTheme.colors.backgroundColor)
    ) {
        Button(
            onClick = onClick,
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
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SDKTheme.colors.brand,
                disabledBackgroundColor = SDKTheme.colors.brand.copy(alpha = 0.3f)
            ),
            shape = SDKTheme.shapes.radius6,
            modifier = modifier
                .height(45.dp)
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