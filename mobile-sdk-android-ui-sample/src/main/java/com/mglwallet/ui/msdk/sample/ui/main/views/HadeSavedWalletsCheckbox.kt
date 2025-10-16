package com.mglwallet.ui.msdk.sample.ui.main.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.mglwallet.ui.msdk.sample.ui.components.SDKCheckbox

@Composable
internal fun HideSavedWalletsCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SDKCheckbox(
        modifier = Modifier
            .testTag(""),
        text = "Hide saved wallets",
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}