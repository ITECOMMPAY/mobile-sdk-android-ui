package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.forcePaymentMethod

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

@Composable
internal fun ForcePaymentMethodCheckbox(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                viewModel.pushIntent(MainViewIntents.ChangeForcePaymentMethodCheckbox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewState?.isVisibleForcePaymentMethodFields == true,
            onCheckedChange = { viewModel.pushIntent(MainViewIntents.ChangeForcePaymentMethodCheckbox) },
        )
        Text(text = "Custom force payment method")
    }
    if (viewState?.isVisibleForcePaymentMethodFields == true) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = Color.LightGray),
            content = {
                SelectForcePaymentMethod()
            }
        )
    }
}