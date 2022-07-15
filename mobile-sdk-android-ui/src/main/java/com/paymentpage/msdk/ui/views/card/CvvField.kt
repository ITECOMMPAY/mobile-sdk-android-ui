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
    modifier: Modifier,
    length: Int = 3,
    onValueEntered: (String) -> Unit,
) {
    CustomTextField(
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        onRequestValidatorMessage = {
            if (it.length != length)
                PaymentActivity.stringResourceManager.getStringByKey("message_invalid_cvv")
            else
                null
        },
        onValueChanged = {
            if (it.length == length)
                onValueEntered(it)
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
        onValueEntered = {}
    )
}