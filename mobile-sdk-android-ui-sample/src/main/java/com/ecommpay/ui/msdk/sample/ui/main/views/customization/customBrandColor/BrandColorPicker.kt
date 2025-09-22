package com.ecommpay.ui.msdk.sample.ui.main.views.customization.customBrandColor

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
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewState
import com.ecommpay.ui.msdk.sample.utils.HexToJetpackColor
import com.ecommpay.ui.msdk.sample.utils.extensions.toHexCode
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

private const val defaultPrimaryBrandColor = "#00579E"
private const val defaultSecondaryBrandColor = "#CAB2FF"

@Composable
internal fun BrandColorPicker(
    isPrimaryColor: Boolean,
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    val labelText = when(isPrimaryColor) {
        true -> "Primary color"
        else -> "Secondary color"
    }

    val color = when(isPrimaryColor) {
        true -> viewState.primaryBrandColor
        else -> viewState.secondaryBrandColor
    }

    val dialogState = rememberMaterialDialogState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .testTag("brandColorTextField"),
            value = color,
            onValueChange = {
                val color = (if (it.length in 0..7) it else it.substring(0, 7)).uppercase()

                intentListener(
                    MainViewIntents.ChangeBrandColor(
                        primaryBrandColor = color.takeIf { isPrimaryColor },
                        secondaryBrandColor = color.takeIf { isPrimaryColor.not() }
                    )
                )
            },
            label = { Text(text = labelText) }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
        ) {
            val color = when(isPrimaryColor) {
                true -> viewState.primaryBrandColor
                else -> viewState.secondaryBrandColor
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = HexToJetpackColor.getColor(color) ?: Color.White
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
                    primaryBrandColor = defaultPrimaryBrandColor,
                    secondaryBrandColor = defaultSecondaryBrandColor,
                )
            )
        }
    ) {
        Text(text = "Reset brand color to default", color = Color.White, fontSize = 18.sp)
    }
    ColorPickerDialog(dialogState = dialogState) {
        intentListener(
            MainViewIntents.ChangeBrandColor(
                primaryBrandColor = it.toHexCode(),
                secondaryBrandColor = it.toHexCode(),
            )
        )
    }
}