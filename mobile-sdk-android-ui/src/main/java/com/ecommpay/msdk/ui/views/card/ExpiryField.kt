package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.core.domain.entities.SdkExpiry
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.utils.MaskVisualTransformation
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun ExpiryField(
    modifier: Modifier,
    value: String? = null,
    isDisabled: Boolean = false,
    onValueEntered: (String) -> Unit,
) {
    CustomTextField(
        initialValue = value?.replace("/", ""),
        modifier = modifier,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        onValidate = {
            val expiryDate = SdkExpiry(it)
            when {
                !expiryDate.isValid() ->
                    PaymentActivity.stringResourceManager.getStringByKey("message_about_expiry")
                else -> null
            }
        },
        onValueChanged = {
            val expiryDate = SdkExpiry(it)
            if (expiryDate.month != null && expiryDate.year != null)
                onValueEntered(expiryDate.stringValue)
        },
        visualTransformation = MaskVisualTransformation("##/##"),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_expiry"),
        isDisabled = isDisabled,
        keyboardType = KeyboardType.Number,
        maxLength = 4,
        isRequired = true
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