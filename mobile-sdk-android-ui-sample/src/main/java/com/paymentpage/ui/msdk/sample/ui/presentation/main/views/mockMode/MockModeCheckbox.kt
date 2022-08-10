package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.mockMode

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun MockModeCheckbox(
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
                viewModel.pushIntent(MainViewIntents.ChangeMockModeCheckbox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewState?.isVisibleMockModeType == true,
            onCheckedChange = { viewModel.pushIntent(MainViewIntents.ChangeMockModeCheckbox) },
        )
        Text(text = "Custom mock mode")
    }
    if (viewState?.isVisibleMockModeType == true) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.LightGray)
                .padding(horizontal = 10.dp),
            content = {
                Spacer(modifier = Modifier.size(10.dp))
                SelectMockMode()
                Spacer(modifier = Modifier.size(10.dp))
            }
        )
    }
}