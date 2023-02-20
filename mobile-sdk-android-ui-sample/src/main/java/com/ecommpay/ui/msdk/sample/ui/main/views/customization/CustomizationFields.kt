package com.ecommpay.ui.msdk.sample.ui.main.views.customization

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewState
import com.ecommpay.ui.msdk.sample.ui.main.views.customization.customBrandColor.BrandColorPicker
import com.ecommpay.ui.msdk.sample.ui.main.views.customization.customLogo.SelectImagesList

@Composable
internal fun CustomizationFields(
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    Spacer(modifier = Modifier.size(10.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.LightGray)
            .padding(horizontal = 10.dp),
        content = {
            ThemeCheckbox(
                isChecked = viewState.isDarkTheme
            ) {
                intentListener(
                    MainViewIntents.ChangeThemeCheckbox
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            BrandColorPicker(
                viewState = viewState,
                intentListener = intentListener
            )
            Spacer(modifier = Modifier.size(10.dp))
            SelectImagesList(
                selectedResourceImageId = viewState.selectedResourceImageId,
                viewState = viewState,
                intentListener = intentListener
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    )
}