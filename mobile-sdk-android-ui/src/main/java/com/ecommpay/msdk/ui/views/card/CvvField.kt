package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun CvvField(
    modifier: Modifier,
    length: Int = 3,
    onCvvEntered: (String) -> Unit,
) {
    var cvv by remember { mutableStateOf("") }
    CustomTextField(
        modifier = modifier,
        value = cvv,
        keyboardType = KeyboardType.Number,
        onTransform = { value -> value.filter { it.isDigit() } },
        onValueChange = {
            if (it.length <= length) {
                cvv = it
                if (it.length == length)
                    onCvvEntered(it)
            }
        },
        visualTransformation = PasswordVisualTransformation(),
        label = PaymentActivity.stringResourceManager.getStringByKey("title_cvv")
            ?: stringResource(R.string.cvv_title),
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