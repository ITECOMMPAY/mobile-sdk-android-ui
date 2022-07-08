package com.ecommpay.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun CustomTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    onValidate: ((String) -> String?)? = null,
    onTransform: ((String) -> String)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    nextFocus: FocusRequester? = null,
    label: String,
    isDisabled: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
) {

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = if (nextFocus != null) ImeAction.Next else ImeAction.Done
    )
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            trailingIcon = trailingIcon,
            value = value,
            onValueChange = {
                var text = it
                if (onTransform != null)
                    text = onTransform.invoke(text)
                errorMessage = onValidate?.invoke(text)
                onValueChange(text)
            },
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                disabledLabelColor = SDKTheme.colors.disabledTextColor,
                disabledTextColor = SDKTheme.colors.disabledTextColor,
                textColor = SDKTheme.colors.primaryTextColor,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            enabled = !isDisabled,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = when {
                        errorMessage != null -> SDKTheme.colors.borderErrorColor
                        else -> SDKTheme.colors.borderColor
                    },
                    shape = SDKTheme.shapes.radius6
                )
                .background(
                    color = when {
                        errorMessage != null -> SDKTheme.colors.panelBackgroundErrorColor
                        isDisabled -> SDKTheme.colors.backgroundColor
                        else -> SDKTheme.colors.panelBackgroundColor
                    },
                    shape = SDKTheme.shapes.radius6
                )
                .height(52.dp),
            label = {
                Text(
                    label,
                    color = if (isDisabled) SDKTheme.colors.disabledTextColor else SDKTheme.colors.secondaryTextColor
                )
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { nextFocus?.requestFocus() }
            )
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp4))
            Text(
                text = errorMessage ?: "",
                color = SDKTheme.colors.errorTextColor,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun CustomTextFieldPreview() {
    CustomTextField(
        modifier = Modifier,
        value = "VALUE",
        keyboardType = KeyboardType.Number,
        onValueChange = {
        },
        label = "LABEL",
    )
}