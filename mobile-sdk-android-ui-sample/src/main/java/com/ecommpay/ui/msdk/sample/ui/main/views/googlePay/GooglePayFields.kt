package com.ecommpay.ui.msdk.sample.ui.main.views.googlePay

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
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData

@Composable
internal fun GooglePayFields(
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
            value = paymentData.merchantId,
            onValueChange = {
                intentListener(
                    MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(merchantId = it)))
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Merchant Id") }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = paymentData.merchantName,
            onValueChange = {
                intentListener(
                    MainViewIntents.ChangeField(
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
                intentListener(
                    MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        merchantId = PaymentData().merchantId,
                        merchantName = PaymentData().merchantName
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