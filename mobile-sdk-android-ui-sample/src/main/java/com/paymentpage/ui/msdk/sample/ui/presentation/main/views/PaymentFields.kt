package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import java.util.*

@Composable
internal fun PaymentFields(
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit
) {
    val context = LocalContext.current
    OutlinedTextField(
        value = paymentData.paymentId,
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(paymentId = it)))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Payment Id") },
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        val clipboardManager = context.getSystemService(
                            ComponentActivity.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText("PaymentId", paymentData.paymentId)
                        clipboardManager.setPrimaryClip(clipData)
                        Toast.makeText(context, "PaymentId in clipboard", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        intentListener(MainViewIntents.ChangeField(
                            paymentData = paymentData.copy(paymentId = "sdk_sample_ui_${UUID.randomUUID().toString().take(8)}")
                        ))
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                }
            }
        }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.paymentAmount?.toString() ?: "",
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData =
                paymentData.copy(paymentAmount = it.filter { symbol ->
                    symbol.isDigit()
                }.toLongOrNull())))
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(text = "Payment Amount") }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.paymentCurrency,
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(paymentCurrency = it.uppercase())))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Payment Currency") }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.paymentDescription,
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(paymentDescription = it)))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Payment Description") }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.customerId,
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(customerId = it)))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Customer Id") }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.languageCode,
        onValueChange = {
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(languageCode = it)))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Language code") }
    )
    Spacer(modifier = Modifier.size(10.dp))
    OutlinedTextField(
        value = paymentData.forcePaymentMethod,
        onValueChange = { changingString ->
            intentListener(MainViewIntents.ChangeField(
                paymentData = paymentData.copy(forcePaymentMethod = changingString)))
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Force payment method") }
    )
}