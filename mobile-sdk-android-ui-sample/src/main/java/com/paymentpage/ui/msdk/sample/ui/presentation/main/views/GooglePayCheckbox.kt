package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun GooglePayCheckbox(
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
                viewModel.pushIntent(MainViewIntents.ChangeGooglePayCheckBox)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = viewState?.isVisibleGooglePayFields == true,
            onCheckedChange = { viewModel.pushIntent(MainViewIntents.ChangeGooglePayCheckBox) },
        )
        Text(text = "Change Google pay params")
    }
    if (viewState?.isVisibleGooglePayFields == true) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray)
            .padding(horizontal = 10.dp)
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = paymentData.merchantId,
                onValueChange = {
                    viewModel.pushIntent(MainViewIntents.ChangeField(
                        paymentData = paymentData.copy(merchantId = it)))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Merchant Id") }
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = paymentData.merchantName,
                onValueChange = {
                    viewModel.pushIntent(MainViewIntents.ChangeField(
                        paymentData = paymentData.copy(merchantName = it)))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Merchant Name") }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    viewModel.pushIntent(MainViewIntents.ChangeField(
                        paymentData = paymentData.copy(
                            merchantId = PaymentData.defaultPaymentData.merchantId,
                            merchantName = PaymentData.defaultPaymentData.merchantName
                        )
                    ))
                }
            ) {
                Text(
                    text = "Reset changes to default",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}