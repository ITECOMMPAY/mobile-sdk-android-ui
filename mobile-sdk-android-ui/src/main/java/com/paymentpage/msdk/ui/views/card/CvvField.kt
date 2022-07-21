package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun CvvField(
    initialValue: String? = null,
    modifier: Modifier,
    length: Int = 3,
    onValueChanged: (String, Boolean) -> Unit,
) {
    CustomTextField(
        initialValue = initialValue,
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        onRequestValidatorMessage = {
            if (it.length != length)
                PaymentActivity.stringResourceManager.getStringByKey("message_invalid_cvv")
            else
                null
        },
        onValueChanged = { value, isValid ->
            onValueChanged(value, value.length == length && isValid)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_cvv"),
        maxLength = length,
        isRequired = true
    )
}

@Composable
@Preview(showBackground = true)
private fun CvvFieldPreview() {
    CvvField(
        modifier = Modifier,
        onValueChanged = { _, _ -> }
    )
}