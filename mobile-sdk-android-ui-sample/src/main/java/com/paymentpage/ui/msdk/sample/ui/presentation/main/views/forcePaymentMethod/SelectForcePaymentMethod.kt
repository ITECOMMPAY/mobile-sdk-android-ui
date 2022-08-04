package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.forcePaymentMethod

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.ui.EcmpPaymentMethodType
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData

@Composable
internal fun SelectForcePaymentMethod(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    EcmpPaymentMethodType.values()
        .filter { it != EcmpPaymentMethodType.APPLE_PAY }
        .forEachIndexed { index, methodType ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == viewState?.selectedForcePaymentMethodId,
                    onClick = {
                        viewModel.pushIntent(
                            MainViewIntents.SelectForcePaymentMethod(
                                id = index,
                                paymentData = viewState?.paymentData?.copy(forcePaymentMethod = methodType)
                                    ?: PaymentData.defaultPaymentData
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = methodType.name.replace("_", " "))
            }
        }
    Spacer(modifier = Modifier.size(10.dp))
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClick = {
            viewModel.pushIntent(
                MainViewIntents.SelectForcePaymentMethod(
                    id = -1,
                    paymentData = viewState?.paymentData?.copy(forcePaymentMethod = null)
                        ?: PaymentData.defaultPaymentData
                )
            )
        }) {
        Text(text = "Reset force payment method", color = Color.White, fontSize = 18.sp)
    }
    Spacer(modifier = Modifier.size(10.dp))
}