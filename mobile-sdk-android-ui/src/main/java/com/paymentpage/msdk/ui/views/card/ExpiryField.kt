package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.MaskVisualTransformation
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun ExpiryField(
    modifier: Modifier,
    shape: Shape = SDKTheme.shapes.radius16,
    initialValue: String? = null,
    scanningExpiry: String? = null,
    errorMessage: String? = null,
    isDisabled: Boolean = false,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
    onRequestValidatorMessage: ((String) -> String?),
) {
    var isFieldInvalid by remember { mutableStateOf(false) }

    CustomTextField(
        modifier = modifier,
        initialValue = initialValue?.replace("/", ""),
        pastedValue = scanningExpiry?.replace("/", ""),
        externalErrorMessage = errorMessage,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        shape = shape,
        isError = isFieldInvalid,
        onRequestValidatorMessage = { text ->
            val validatorResponse = onRequestValidatorMessage.invoke(text)

            isFieldInvalid = validatorResponse != null

            null
        },
        onValueChanged = { value, isValid ->
            val expiryDate = SdkExpiry(value)
            onValueChanged(
                expiryDate.stringValue,
                expiryDate.isValid() && isValid && expiryDate.isMoreThanNow()
            )
        },
        visualTransformation = MaskVisualTransformation("##/##"),
        label = getStringOverride(OverridesKeys.TITLE_EXPIRY),
        isDisabled = isDisabled,
        keyboardType = KeyboardType.Number,
        maxLength = 4,
        isRequired = true,
        testTag = testTag
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreview() {
    ExpiryField(
        modifier = Modifier,
        initialValue = "02/30",
        onValueChanged = { _, _ -> },
        onRequestValidatorMessage = { _ -> null },
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreviewDisabled() {
    ExpiryField(
        isDisabled = true,
        modifier = Modifier,
        initialValue = "02/30",
        onValueChanged = { _, _ -> },
        onRequestValidatorMessage = { _ -> null },
    )
}