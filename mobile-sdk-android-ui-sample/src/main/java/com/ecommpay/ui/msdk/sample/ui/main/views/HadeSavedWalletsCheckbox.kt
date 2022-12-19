package com.ecommpay.ui.msdk.sample.ui.main.views

import androidx.compose.runtime.Composable
import com.ecommpay.ui.msdk.sample.ui.components.SDKCheckbox

@Composable
internal fun HideSavedWalletsCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SDKCheckbox(
        text = "Hide saved wallets",
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}