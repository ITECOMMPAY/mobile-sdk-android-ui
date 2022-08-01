package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun ProjectSettings(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    val paymentData = viewState?.paymentData ?: PaymentData.defaultPaymentData
    OutlinedTextField(
        value = paymentData.projectId?.toString() ?: "",
        onValueChange = {
            viewModel.pushIntent(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(projectId = it.filter { symbol ->
                    symbol.isDigit()
                }
                    .toIntOrNull()))
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Project Id") }
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = paymentData.secretKey,
        onValueChange = {
            viewModel.pushIntent(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(secretKey = it))
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Secret Key") }
    )
}