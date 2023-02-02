package com.ecommpay.ui.msdk.sample.ui.main.views.button

import androidx.compose.runtime.Composable
import com.ecommpay.ui.msdk.sample.ui.components.SDKButton

@Composable
internal fun AuthButton(listener: () -> Unit) {
    SDKButton(
        text = "Auth",
        listener = listener
    )
}