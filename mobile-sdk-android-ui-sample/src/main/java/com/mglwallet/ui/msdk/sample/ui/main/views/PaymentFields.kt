package com.mglwallet.ui.msdk.sample.ui.main.views

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mglwallet.ui.msdk.sample.domain.entities.PaymentData
import com.mglwallet.ui.msdk.sample.domain.ui.main.MainViewIntents
import java.util.UUID

@Composable
internal fun PaymentFields(
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit
) {
    val padding = 10.dp
    val context = LocalContext.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("paymentIdTextField"),
        value = paymentData.paymentId,
        onValueChange = {
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(paymentId = it)
                )
            )
        },
        label = { Text(text = "Payment Id") },
        trailingIcon = {
            Row {
                IconButton(
                    onClick = {
                        val clipboardManager = context.getSystemService(
                            ComponentActivity.CLIPBOARD_SERVICE
                        ) as ClipboardManager
                        val clipData = ClipData.newPlainText("PaymentId", paymentData.paymentId)
                        clipboardManager.setPrimaryClip(clipData)
                        Toast.makeText(context, "PaymentId in clipboard", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        intentListener(
                            MainViewIntents.ChangeField(
                                paymentData = paymentData.copy(
                                    paymentId = "sdk_sample_ui_${
                                        UUID.randomUUID().toString().take(8)
                                    }"
                                )
                            )
                        )
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                }
            }
        }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("paymentAmountTextField"),
        value = if (paymentData.paymentAmount.toString() == "-1")
            ""
        else
            paymentData.paymentAmount.toString(),
        onValueChange = {
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData =
                    paymentData.copy(
                        paymentAmount = it.filter { symbol ->
                            symbol.isDigit()
                        }.toLongOrNull() ?: -1
                    )
                )
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        label = { Text(text = "Payment Amount") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("paymentCurrencyTextField"),
        value = paymentData.paymentCurrency,
        onValueChange = {
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        paymentCurrency = it.uppercase()
                    )
                )
            )
        },
        label = { Text(text = "Payment Currency") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("paymentDescriptionTextField"),
        value = paymentData.paymentDescription ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        paymentDescription = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Payment Description") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("customerIdTextField"),
        value = paymentData.customerId ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        customerId = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Customer Id") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("languageCodeTextField"),
        value = paymentData.languageCode ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        languageCode = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Language code") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("regionCodeTextField"),
        value = paymentData.regionCode ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        regionCode = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Region code") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("forcePaymentMethodTextField"),
        value = paymentData.forcePaymentMethod ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        forcePaymentMethod = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Force payment method") }
    )
    Spacer(modifier = Modifier.size(padding))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("tokenTextField"),
        value = paymentData.token ?: "",
        onValueChange = { changingString ->
            intentListener(
                MainViewIntents.ChangeField(
                    paymentData = paymentData.copy(
                        token = changingString.ifBlank { null }
                    )
                )
            )
        },
        label = { Text(text = "Token") }
    )
}