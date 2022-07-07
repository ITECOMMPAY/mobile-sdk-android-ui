package com.ecommpay.msdk.ui.views.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun ExpiryField(
    modifier: Modifier,
    value: String = "",
    isDisabled: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = SDKTheme.colors.gray,
                shape = SDKTheme.shapes.radius6
            )
            .height(50.dp),
        colors = TextFieldDefaults.textFieldColors(
            disabledLabelColor = SDKTheme.colors.disabledTextColor,
            disabledTextColor = SDKTheme.colors.disabledTextColor,
            textColor = SDKTheme.colors.primaryTextColor,
            backgroundColor = SDKTheme.colors.backgroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = value,
        onValueChange = {
            if (!isDisabled) {
                onValueChange
            }
        },
        enabled = !isDisabled,
        label = {
            Text(
                PaymentActivity.stringResourceManager.getStringByKey("title_expiry")
                    ?: stringResource(R.string.expiry_title),
                color = if (isDisabled) SDKTheme.colors.disabledTextColor else SDKTheme.colors.secondaryTextColor
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreview() {
    ExpiryField(
        modifier = Modifier,
        value = "02/30",
        onValueChange = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreviewDisabled() {
    ExpiryField(
        isDisabled = true,
        modifier = Modifier,
        value = "02/30",
        onValueChange = {}
    )
}