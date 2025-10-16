package com.mglwallet.ui.msdk.sample.ui.main.views.customization.customBrandColor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mglwallet.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.mglwallet.ui.msdk.sample.domain.ui.main.MainViewState
import com.mglwallet.ui.msdk.sample.utils.HexToJetpackColor
import com.mglwallet.ui.msdk.sample.utils.extensions.toHexCode
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
internal fun BrandColorPicker(
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    val dialogState = rememberMaterialDialogState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .testTag("brandColorTextField"),
            value = if (viewState.brandColor.isNotEmpty())
                viewState.brandColor.uppercase()
            else "#",
            onValueChange = {
                intentListener(
                    MainViewIntents.ChangeBrandColor(
                        brandColor = (if (it.length in 0..7) it else it.substring(0, 7)).uppercase()
                    )
                )
            },
            label = { Text(text = "Brand color") }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = HexToJetpackColor.getColor(viewState.brandColor) ?: Color.White
                    )
                    .clickable {
                        dialogState.show()
                    }
            )
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("resetBrandColorButton"),
        onClick = {
            intentListener(
                MainViewIntents.ChangeBrandColor(
                    brandColor = "#00579E"
                )
            )
        }
    ) {
        Text(text = "Reset brand color to default", color = Color.White, fontSize = 18.sp)
    }
    ColorPickerDialog(dialogState = dialogState) {
        intentListener(
            MainViewIntents.ChangeBrandColor(
                brandColor = it.toHexCode()
            )
        )
    }
}