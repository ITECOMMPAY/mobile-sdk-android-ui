package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKColorInput
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.defaults.SdkColorDefaults

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CustomTextField(
    modifier: Modifier = Modifier,
    pastedValue: String? = null,
    initialValue: String? = null,
    isError: Boolean = false,
    externalErrorMessage: String? = null,
    textStyle: TextStyle? = null,
    singleLine: Boolean = false,
    shape: Shape = SDKTheme.shapes.radius12,
    color: SDKColorInput = SdkColorDefaults.inputColor(),
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
    isRequired: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    contentDescriptionValue: String? = null,
    testTag: String? = null,
) {
    var textValue by remember { mutableStateOf(initialValue ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardOptions = remember(keyboardType, nextFocus) {
        KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = if (nextFocus != null) ImeAction.Next else ImeAction.Done
        )
    }
    val requiredFieldContentDescription =
        stringResource(id = R.string.required_field_content_description)
    val onValueChange: (String) -> Unit = {
        var text =
            if (maxLength != null && it.length > maxLength)
                it.substring(0 until maxLength)
            else
                it
        if (onFilterValueBefore != null)
            text = onFilterValueBefore(text)
        textValue = text
        var isValid = true
        if (isRequired)
            isValid = textValue.isNotEmpty()
        if (onValueChanged != null) {
            onValueChanged(text, isValid)
        }
    }

    if (pastedValue != null)
        LaunchedEffect(key1 = pastedValue) {
            textValue = pastedValue
            onValueChange(pastedValue)

            errorMessage = onRequestValidatorMessage?.invoke(pastedValue)
        }

    Column(modifier = modifier.fillMaxWidth()) {
        val disabledTextColor = color.textAdditional().value
        val defaultBackground = color.defaultBackground()

        TextField(
            trailingIcon = trailingIcon,
            value = textValue,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                disabledLabelColor = disabledTextColor,
                disabledTextColor = disabledTextColor,
                textColor = color.textPrimary().value,
                backgroundColor = when {
                    isFocused -> color.focusedBackground().value
                    (isError || errorMessage != null) -> SDKTheme.colors.containerRed
                    isDisabled -> color.disabledBackground().value
                    else -> defaultBackground.value
                },
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = SDKTheme.colors.primary,
            ),
            enabled = !isDisabled,
            readOnly = !isEditable,
            isError = isError,
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = when {
                        isFocused -> color.focusedBorder().value
                        (isError || errorMessage != null) -> color.errorBorder().value
                        isDisabled -> Color.Transparent
                        else -> Color.Transparent
                    },
                    shape = shape
                )
                .onFocusChanged {
                    val unfocused = !it.isFocused && isFocused
                    val focused = it.isFocused && !isFocused

                    when {
                        unfocused -> {
                            errorMessage = onRequestValidatorMessage?.invoke(textValue)
                        }

                        focused -> {
                            errorMessage = null
                        }
                    }

                    isFocused = it.isFocused

                    onFocusChanged?.invoke(isFocused)
                }
                .semantics {
                    contentDescription =
                        "${
                            if (!contentDescriptionValue.isNullOrEmpty())
                                contentDescriptionValue
                            else ""
                        }${
                            if (isRequired) " $requiredFieldContentDescription" else ""
                        }"
                }
                .testTag(testTag ?: ""),
            label = {
                Row {
                    Text(
                        text = label,
                        color = when {
                            isFocused -> color.textPrimary().value
                            isDisabled -> color.textAdditional().value
                            else -> when {
                                (isError || errorMessage != null) && !isFocused && textValue.isNotEmpty() -> color.errorBorder().value
                                else -> color.textPrimary().value
                            }
                        },
                        maxLines = 1
                    )
                    if (isRequired.not()) {
                        Text(
                            modifier = Modifier
                                .semantics {
                                    invisibleToUser()
                                },
                            text = " ", // TODO Replace to actual string from Override dictionary
                        )

                        Text(
                            modifier = Modifier,
                            text = "(optional)", // TODO Replace to actual string from Override dictionary
                            color = SdkColorDefaults.inputColor().textAdditional().value,
                            maxLines = 1
                        )
                    }
                }
            },
            placeholder = {
                if (!placeholder.isNullOrEmpty())
                    Text(
                        placeholder,
                        color = if (isDisabled) color.textAdditional().value else color.textPrimary().value,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
            },
            textStyle = textStyle ?: TextStyle(fontSize = 16.sp),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onNext = { nextFocus?.requestFocus() }),
        )

        if (errorMessage != null || externalErrorMessage != null) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = errorMessage ?: externalErrorMessage.orEmpty(),
                color = SDKTheme.colors.red,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun CustomTextFieldPreview() {
    CustomTextField(
        placeholder = "VALUE",
        initialValue = "0007",
        keyboardType = KeyboardType.Number,
        onValueChanged = null,
        label = "LABEL",
        isDisabled = true,
    )
}