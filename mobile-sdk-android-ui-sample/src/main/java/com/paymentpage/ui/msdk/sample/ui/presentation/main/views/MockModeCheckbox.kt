package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
internal fun MockModeCheckbox(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    val paymentData = viewState?.paymentData ?: PaymentData.defaultPaymentData
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                viewModel.pushIntent(MainViewIntents.ChangeField(
                    paymentData =
                    paymentData.copy(mockModeEnabled = !paymentData.mockModeEnabled)))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = paymentData.mockModeEnabled,
            onCheckedChange = {
                viewModel.pushIntent(MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(mockModeEnabled = it)))
            },
        )
        Text(text = "Mock mode enabled")
    }
}