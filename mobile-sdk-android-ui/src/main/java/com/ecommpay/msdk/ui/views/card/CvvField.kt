package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun CvvField(
    modifier: Modifier,
    length: Int = 3,
    onCvvEntered: (String) -> Unit,
) {
    CustomTextField(
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        onValueChange = {
            if (it.length == length)
                onCvvEntered(it)
        },
        visualTransformation = PasswordVisualTransformation(),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_cvv"),
        maxLength = length
    )
}

@Composable
@Preview(showBackground = true)
private fun CvvFieldPreview() {
    CvvField(
        modifier = Modifier,
        onCvvEntered = {}
    )
}