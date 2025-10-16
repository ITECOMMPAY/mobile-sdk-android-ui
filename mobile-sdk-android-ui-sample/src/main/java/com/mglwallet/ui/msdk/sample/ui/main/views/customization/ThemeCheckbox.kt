package com.mglwallet.ui.msdk.sample.ui.main.views.customization

import androidx.compose.runtime.Composable
import com.mglwallet.ui.msdk.sample.ui.components.SDKCheckbox

@Composable
internal fun ThemeCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SDKCheckbox(
        text = "isDarkTheme",
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}