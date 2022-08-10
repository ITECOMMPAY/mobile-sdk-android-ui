package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.mockMode

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.ui.PaymentSDK.*

@Composable
internal fun SelectMockMode(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    MockModeType.values()
        .forEachIndexed { index, mockModeType ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == viewState?.selectedMockModeTypeId,
                    onClick = {
                        viewModel.pushIntent(
                            MainViewIntents.SelectMockMode(
                                id = index,
                                paymentData = viewState?.paymentData?.copy(mockModeType = mockModeType)
                                    ?: PaymentData.defaultPaymentData
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = mockModeType.name)
            }
        }
    Spacer(modifier = Modifier.size(10.dp))
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClick = {
            viewModel.pushIntent(
                MainViewIntents.SelectMockMode(
                    id = 0,
                    paymentData = viewState?.paymentData?.copy(mockModeType = MockModeType.DISABLED)
                        ?: PaymentData.defaultPaymentData
                )
            )
        }) {
        Text(text = "Reset mock mode type", color = Color.White, fontSize = 18.sp)
    }
    Spacer(modifier = Modifier.size(10.dp))
}