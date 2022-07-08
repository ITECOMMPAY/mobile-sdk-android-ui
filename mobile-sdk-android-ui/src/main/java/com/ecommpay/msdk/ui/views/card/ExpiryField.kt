package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.core.domain.entities.init.ExpiryDate
import com.ecommpay.msdk.core.validators.custom.ExpiryFromStringValidator
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.utils.card.formatExpiration
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun ExpiryField(
    modifier: Modifier,
    value: String = "",
    isDisabled: Boolean = false,
    onValueEntered: (String) -> Unit,
) {
    var expiry by remember { mutableStateOf(value.replace("/", "")) }
    val errorMessage = PaymentActivity.stringResourceManager.getStringByKey("message_about_expiry")
        ?: stringResource(R.string.expiry_invalid_message)
    CustomTextField(
        modifier = modifier,
        value = expiry,
        onTransform = { text -> text.filter { it.isDigit() } },
        onValidate = {
            when {
                !ExpiryFromStringValidator().isValid(it) -> errorMessage
                else -> null
            }
        },
        onValueChange = {
            val text = if (it.length > 4) it.substring(0..3) else it
            expiry = text
            val expiryDate = ExpiryDate(text)
            if (expiryDate.month != null && expiryDate.year != null)
                onValueEntered(expiryDate.stringValue)
        },
        visualTransformation = { formatExpiration(it) },
        label = PaymentActivity.stringResourceManager.getStringByKey("title_expiry")
            ?: stringResource(R.string.expiry_title),
        isDisabled = isDisabled
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreview() {
    ExpiryField(
        modifier = Modifier,
        value = "02/30",
        onValueEntered = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreviewDisabled() {
    ExpiryField(
        isDisabled = true,
        modifier = Modifier,
        value = "02/30",
        onValueEntered = {}
    )
}