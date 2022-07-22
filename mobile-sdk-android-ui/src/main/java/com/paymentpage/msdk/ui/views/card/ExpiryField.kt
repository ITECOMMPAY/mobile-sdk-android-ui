package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.utils.MaskVisualTransformation
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun ExpiryField(
    modifier: Modifier,
    initialValue: String? = null,
    isDisabled: Boolean = false,
    onValueChanged: (String, Boolean) -> Unit,
) {
    CustomTextField(
        initialValue = initialValue?.replace("/", ""),
        modifier = modifier,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        onRequestValidatorMessage = {
            val expiryDate = SdkExpiry(it)
            when {
                !expiryDate.isValid() ->
                    PaymentActivity.stringResourceManager.getStringByKey("message_about_expiry")
                else -> null
            }
        },
        onValueChanged = { value, isValid ->
            val expiryDate = SdkExpiry(value)
            onValueChanged(
                expiryDate.stringValue,
                expiryDate.isValid() && isValid
            )
        },
        visualTransformation = MaskVisualTransformation("##/##"),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_expiry"),
        placeholder = PaymentActivity.stringResourceManager.getStringByKey("title_expiration_placeholder"),
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
        initialValue = "02/30",
        onValueChanged = { _, _ -> }
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreviewDisabled() {
    ExpiryField(
        isDisabled = true,
        modifier = Modifier,
        initialValue = "02/30",
        onValueChanged = { _, _ -> }
    )
}