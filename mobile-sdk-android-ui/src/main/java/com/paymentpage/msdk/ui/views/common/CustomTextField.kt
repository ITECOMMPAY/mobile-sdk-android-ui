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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
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
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CustomTextField(
    modifier: Modifier = Modifier,
    pastedValue: String? = null,
    initialValue: String? = null,
    textStyle: TextStyle? = null,
    singleLine: Boolean = false,
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
    contentDescriptionValue: String? = null,
    testTag: String? = null
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
            errorMessage =
                if (isRequired && pastedValue.isEmpty())
                    getStringOverride(OverridesKeys.MESSAGE_REQUIRED_FIELD)
                else if (pastedValue.isNotEmpty())
                    onRequestValidatorMessage?.invoke(pastedValue)
                else null
        }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            trailingIcon = trailingIcon,
            value = textValue,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                disabledLabelColor = SDKTheme.colors.mediumGrey,
                disabledTextColor =
                if (!SDKTheme.colors.isDarkTheme)
                    SDKTheme.colors.mediumGrey
                else
                    SDKTheme.colors.grey,
                textColor = SDKTheme.colors.neutral,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = SDKTheme.colors.primary,
            ),
            enabled = !isDisabled,
            readOnly = !isEditable,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (!isFocused) 1.dp else 2.dp,
                    color = when {
                        errorMessage != null -> SDKTheme.colors.red
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
                    shape = SDKTheme.shapes.radius6
                )
                .background(
                    color = when {
                        errorMessage != null -> SDKTheme.colors.containerRed
                        isFocused -> SDKTheme.colors.accent
                        isDisabled && !SDKTheme.colors.isDarkTheme -> SDKTheme.colors.background
                        isDisabled && SDKTheme.colors.isDarkTheme -> SDKTheme.colors.container
                        else -> SDKTheme.colors.inputField
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
                            else ->
                                if (!SDKTheme.colors.isDarkTheme)
                                    SDKTheme.colors.grey
                                else if (errorMessage != null && !isFocused && textValue.isNotEmpty())
                                    SDKTheme.colors.red
                                else
                                    SDKTheme.colors.neutral
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
                            SDKTheme.colors.grey,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )
            },
            textStyle = textStyle ?: TextStyle(fontSize = 16.sp),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onNext = { nextFocus?.requestFocus() }
            ),
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = errorMessage ?: "",
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
        initialValue = "VALUE",
        keyboardType = KeyboardType.Number,
        onValueChanged = null,
        label = "LABEL",
    )
}