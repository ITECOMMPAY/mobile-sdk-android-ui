package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
fun CustomTextField(
    modifier: Modifier,
    initialValue: String? = null,
    onValueChanged: (String) -> Unit = {},
    onRequestValidatorMessage: ((String) -> String?)? = null,
    onFilterValueBefore: ((String) -> String)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    nextFocus: FocusRequester? = null,
    label: String,
    placeholder: String? = null,
    isDisabled: Boolean = false,
    isRequired: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null
) {

    var textValue by remember { mutableStateOf(initialValue ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFocused by remember { mutableStateOf(false) }


    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = if (nextFocus != null) ImeAction.Next else ImeAction.Done
    )
    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            trailingIcon = trailingIcon,
            value = textValue,
            onValueChange = {
                var text =
                    if (maxLength != null && it.length > maxLength) it.substring(0 until maxLength) else it
                if (onFilterValueBefore != null)
                    text = onFilterValueBefore(text)


//                errorMessage =
//                    if (isRequired && text.isEmpty())
//                        PaymentActivity.stringResourceManager.getStringByKey("message_required_field")
//                    else
//                        onValidate?.invoke(text)

                textValue = text
                //if (errorMessage == null)
                onValueChanged(text)
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
                .onFocusChanged {
                    if (!it.isFocused && isFocused)
                        errorMessage =
                            if (isRequired && textValue.isEmpty())
                                PaymentActivity.stringResourceManager.getStringByKey("message_required_field")
                            else
                                onRequestValidatorMessage?.invoke(textValue)
                    else if (it.isFocused && !isFocused)
                        errorMessage = null

                    isFocused = it.isFocused
                },
            label = {
                Row {
                    Text(
                        label,
                        color = if (isDisabled) SDKTheme.colors.disabledTextColor else SDKTheme.colors.secondaryTextColor,
                    )
                    if (isRequired) {
                        Text(
                            " *",
                            color = SDKTheme.colors.errorTextColor,
                        )
                    }
                }
            },
            placeholder = {
                if (!placeholder.isNullOrEmpty())
                    Text(
                        placeholder,
                        color = if (isDisabled) SDKTheme.colors.disabledTextColor else SDKTheme.colors.secondaryTextColor,
                        fontSize = 14.sp
                    )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = SDKTheme.colors.primaryTextColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { nextFocus?.requestFocus() }
            )
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding4))
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
        initialValue = "VALUE",
        keyboardType = KeyboardType.Number,
        onValueChanged = {
        },
        label = "LABEL",
    )
}