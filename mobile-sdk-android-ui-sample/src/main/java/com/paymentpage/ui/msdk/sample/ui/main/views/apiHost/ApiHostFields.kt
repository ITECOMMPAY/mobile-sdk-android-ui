package com.paymentpage.ui.msdk.sample.ui.main.views.apiHost

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.domain.entities.PaymentData

@Composable
internal fun ApiHostFields(
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(width = 1.dp, color = Color.LightGray)
        .padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = paymentData.apiHost,
            onValueChange = {
                intentListener(
                    MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(apiHost = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Api Host") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = paymentData.wsApiHost,
            onValueChange = {
                intentListener(
                    MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(wsApiHost = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Ws Api Host") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                intentListener(
                    MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        apiHost = PaymentData().apiHost,
                        wsApiHost = PaymentData().wsApiHost
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