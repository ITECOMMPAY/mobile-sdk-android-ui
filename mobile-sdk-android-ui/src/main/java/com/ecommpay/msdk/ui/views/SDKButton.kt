package com.ecommpay.msdk.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SDKButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        content = {
            Text(text = text)
            if (isLoading)
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(35.dp)
                )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        enabled = isEnabled,
    )
}

@Composable
@Preview
private fun SDKButtonPreview() {
    SDKButton(
        text = "Pay 100 USD",
        isEnabled = true,
        isLoading = false) {
    }
}