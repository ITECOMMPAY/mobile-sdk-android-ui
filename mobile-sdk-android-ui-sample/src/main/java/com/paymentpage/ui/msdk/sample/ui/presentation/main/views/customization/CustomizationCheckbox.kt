package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customBrandColor.BrandColorPicker
import com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customLogo.SelectImagesList

@Composable
internal fun CustomizationCheckbox(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                viewModel.pushIntent(MainViewIntents.ChangeCustomizationCheckbox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewState?.isVisibleCustomizationFields == true,
            onCheckedChange = { viewModel.pushIntent(MainViewIntents.ChangeCustomizationCheckbox) },
        )
        Text(text = "Custom brand color and logo")
    }
    if (viewState?.isVisibleCustomizationFields == true) {
        Spacer(modifier = Modifier.size(10.dp))
        BrandColorPicker()
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = {
                Spacer(modifier = Modifier.height(10.dp))
                SelectImagesList()
                Spacer(modifier = Modifier.height(10.dp))
                viewState?.paymentData?.bitmap?.asImageBitmap()?.let {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.LightGray),
                            bitmap = it,
                            contentDescription = null)
                    }
                }
            }
        )
    }
}