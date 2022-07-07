package com.ecommpay.msdk.ui.views.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun CvvField(
    modifier: Modifier,
    length: Int = 3,
    onCvvEntered: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }
    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = SDKTheme.colors.gray,
                shape = SDKTheme.shapes.radius6
            )
            .height(50.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = SDKTheme.colors.primaryTextColor,
            backgroundColor = SDKTheme.colors.lightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        value = value,
        onValueChange = {
            if (it.length <= length) {
                value = it
                if (it.length == length)
                    onCvvEntered(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = PasswordVisualTransformation(),
        label = {
            Text(
                PaymentActivity.stringResourceManager.getStringByKey("title_cvv")
                    ?: stringResource(R.string.cvv_title),
                color = SDKTheme.colors.secondaryTextColor
            )
        }
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