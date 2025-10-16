package com.mglwallet.ui.msdk.sample.ui.main.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mglwallet.ui.msdk.sample.domain.entities.PaymentData
import com.mglwallet.ui.msdk.sample.domain.ui.main.MainViewIntents

@Composable
internal fun ProjectSettings(
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("projectIdTextField"),
        value = if (paymentData.projectId.toString() == "-1")
            ""
        else
            paymentData.projectId.toString(),
        onValueChange = {
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        projectId = it.filter { symbol ->
                            symbol.isDigit()
                        }.toIntOrNull() ?: -1
                    )
                )
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(text = "Project Id") }
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("secretKeyTextField"),
        value = paymentData.secretKey,
        onValueChange = {
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(secretKey = it)
                )
            )
        },
        label = { Text(text = "Secret Key") }
    )
}