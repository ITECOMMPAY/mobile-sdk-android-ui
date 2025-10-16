package com.mglwallet.ui.msdk.sample.ui.main.views.button

import androidx.compose.runtime.Composable
import com.mglwallet.ui.msdk.sample.ui.components.SDKButton

@Composable
internal fun TokenizeButton(listener: () -> Unit) {
    SDKButton(
        text = "Tokenize",
        listener = listener
    )
}