package com.paymentpage.ui.msdk.sample.ui.main.views.customization

import androidx.compose.runtime.Composable
import com.paymentpage.ui.msdk.sample.ui.components.SDKCheckbox

@Composable
internal fun CustomizationCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SDKCheckbox(
        text = "Custom brand color and logo",
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}