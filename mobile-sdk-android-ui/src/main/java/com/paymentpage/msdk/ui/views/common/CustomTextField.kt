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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    initialValue: String? = null,
    onValueChanged: ((String, Boolean) -> Unit)?,
    onRequestValidatorMessage: ((String) -> String?)? = null,
    onFilterValueBefore: ((String) -> String)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    nextFocus: FocusRequester? = null,
    label: String,
    placeholder: String? = null,
    isDisabled: Boolean = false,
    isEditable: Boolean = true,
    isRequired: Boolean = false,
    showRedStarForRequiredFields: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
) {

    var textValue by remember { mutableStateOf(initialValue ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = value) {
        textValue = value ?: initialValue ?: "" //if you want paste value by yourself
    }

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
                textValue = text
                var isValid = true
                if (isRequired)
                    isValid = textValue.isNotEmpty()
                if (onValueChanged != null) {
                    onValueChanged(text, isValid)
                }
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
                cursorColor = SDKTheme.colors.brand,
            ),
            enabled = !isDisabled,
            readOnly = !isEditable,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = when {
                        errorMessage != null -> SDKTheme.colors.borderErrorColor
                        else -> if (isFocused) SDKTheme.colors.brand else SDKTheme.colors.borderColor
                    },
                    shape = SDKTheme.shapes.radius6
                )
                .background(
                    color = when {
                        errorMessage != null -> SDKTheme.colors.panelBackgroundErrorColor
                        isFocused -> SDKTheme.colors.backgroundTextFieldColor
                        isDisabled -> SDKTheme.colors.backgroundColor
                        else -> SDKTheme.colors.panelBackgroundColor
                    },
                    shape = SDKTheme.shapes.radius6
                )
                .onFocusChanged {
                    if (!it.isFocused && isFocused)
                        errorMessage =
                            if (isRequired && textValue.isEmpty())
                                getStringOverride(OverridesKeys.MESSAGE_REQUIRED_FIELD)
                            else if (textValue.isNotEmpty())
                                onRequestValidatorMessage?.invoke(textValue)
                            else null
                    else if (it.isFocused && !isFocused)
                        errorMessage = null
                    isFocused = it.isFocused
                    if (onFocusChanged != null) {
                        onFocusChanged(isFocused)
                    }
                },
            label = {
                Row {
                    Text(
                        text = label,
                        color = when {
                            isFocused -> SDKTheme.colors.brand
                            isDisabled -> SDKTheme.colors.disabledTextColor
                            else -> SDKTheme.colors.secondaryTextColor
                        },
                        maxLines = 1
                    )
                    if (isRequired && showRedStarForRequiredFields) {
                        Text(
                            text = "*",
                            color = SDKTheme.colors.errorTextColor,
                            maxLines = 1
                        )
                    }
                }
            },
            placeholder = {
                if (!placeholder.isNullOrEmpty())
                    Text(
                        placeholder,
                        color = if (isDisabled) SDKTheme.colors.disabledTextColor else SDKTheme.colors.secondaryTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { nextFocus?.requestFocus() }
            )
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.size(4.dp))
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
        initialValue = "VALUE",
        keyboardType = KeyboardType.Number,
        onValueChanged = null,
        label = "LABEL",
    )
}