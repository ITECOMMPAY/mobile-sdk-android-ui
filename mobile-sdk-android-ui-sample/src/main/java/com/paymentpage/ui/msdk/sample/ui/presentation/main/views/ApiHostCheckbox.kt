package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun ApiHostCheckbox(
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
                viewModel.pushIntent(MainViewIntents.ChangeApiHostCheckBox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewState?.isVisibleApiHostFields == true,
            onCheckedChange = { viewModel.pushIntent(MainViewIntents.ChangeApiHostCheckBox) },
        )
        Text(text = "Change Api Host")
    }
    if (viewState?.isVisibleApiHostFields == true) {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = paymentData.apiHost,
            onValueChange = {
                viewModel.pushIntent(MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(apiHost = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Api Host") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = paymentData.wsApiHost,
            onValueChange = {
                viewModel.pushIntent(MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(wsApiHost = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ws Api Host") }
        )
    }
}