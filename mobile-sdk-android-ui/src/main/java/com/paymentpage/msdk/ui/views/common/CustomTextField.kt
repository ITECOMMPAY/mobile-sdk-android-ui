package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
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
import com.paymentpage.msdk.ui.theme.SDKTheme

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
    shape: Shape = SDKTheme.shapes.radius16,
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
    showRedStarForRequiredFields: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int? = null,
    contentDescriptionValue: String? = null,
    testTag: String? = null,
) {

    var textValue by remember { mutableStateOf(initialValue ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = if (nextFocus != null) ImeAction.Next else ImeAction.Done
    )
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
            //if you want paste value by yourself
            textValue = pastedValue
            onValueChange(pastedValue)

            errorMessage = onRequestValidatorMessage?.invoke(pastedValue)
        }

    Column(modifier = modifier.fillMaxWidth()) {
        val disabledTextColor = if (!SDKTheme.colors.isDarkTheme)
            SDKTheme.colors.mediumGrey
        else
            SDKTheme.colors.grey

        TextField(
            trailingIcon = trailingIcon,
            value = textValue,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                disabledLabelColor = disabledTextColor,
                disabledTextColor = disabledTextColor,
                textColor = SDKTheme.colors.neutral,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = SDKTheme.colors.primary,
            ),
            enabled = !isDisabled,
            readOnly = !isEditable,
            isError = isError,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (!isFocused) 1.dp else 2.dp,
                    color = when {
                        (isError || errorMessage != null) -> SDKTheme.colors.red
                        else ->
                            when {
                                isFocused -> SDKTheme.colors.primary
                                isDisabled -> SDKTheme.colors.inputField
                                else -> if (!SDKTheme.colors.isDarkTheme)
                                    SDKTheme.colors.container
                                else
                                    SDKTheme.colors.inputField
                            }
                    },
                    shape = shape
                )
                .background(
                    color = when {
                        (isError || errorMessage != null) -> SDKTheme.colors.containerRed
                        isFocused -> SDKTheme.colors.accent
                        isDisabled -> Color.Transparent
                        else -> SDKTheme.colors.inputField
                    },
                    shape = shape
                )
                .onFocusChanged {
                    val unfocused = !it.isFocused && isFocused
                    val focused = it.isFocused && !isFocused

                    when {
                        unfocused -> {errorMessage = onRequestValidatorMessage?.invoke(textValue) }
                        focused -> { errorMessage = null }
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
                            if (isRequired && showRedStarForRequiredFields) " $requiredFieldContentDescription" else ""
                        }"
                }
                .testTag(testTag ?: ""),
            label = {
                Row {
                    Text(
                        text = label,
                        color = when {
                            isFocused && !SDKTheme.colors.isDarkTheme -> SDKTheme.colors.primary
                            isFocused && SDKTheme.colors.isDarkTheme -> SDKTheme.colors.neutral
                            isDisabled && !SDKTheme.colors.isDarkTheme -> SDKTheme.colors.mediumGrey
                            isDisabled && SDKTheme.colors.isDarkTheme -> SDKTheme.colors.grey
                            else -> when {
                                (isError || errorMessage != null) && !isFocused && textValue.isNotEmpty() -> SDKTheme.colors.red
                                else -> SDKTheme.colors.neutral
                            }
                        },
                        maxLines = 1
                    )
                    if (isRequired && showRedStarForRequiredFields) {
                        Text(
                            modifier = Modifier
                                .semantics {
                                    invisibleToUser()
                                },
                            text = "*",
                            color = SDKTheme.colors.red,
                            maxLines = 1
                        )
                    }
                }
            },
            placeholder = {
                if (!placeholder.isNullOrEmpty())
                    Text(
                        placeholder,
                        color =
                            if (isDisabled)
                                SDKTheme.colors.mediumGrey
                            else
                                SDKTheme.colors.neutral,
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
        isDisabled = false,
    )
}