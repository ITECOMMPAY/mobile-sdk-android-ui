package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
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
    showRedStarForRequiredFields: Boolean = true,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    CustomTextField(
        modifier = modifier,
        initialValue = initialValue?.replace("/", ""),
        pastedValue = scanningExpiry?.replace("/", ""),
        externalErrorMessage = errorMessage,
        onFilterValueBefore = { text -> text.filter { it.isDigit() } },
        shape = shape,
        onRequestValidatorMessage = {
            val expiryDate = SdkExpiry(it)
            when {
                !expiryDate.isValid() || !expiryDate.isMoreThanNow() ->
                    getStringOverride(OverridesKeys.MESSAGE_ABOUT_EXPIRY)
                else -> null
            }
        },
        onValueChanged = { value, isValid ->
            val expiryDate = SdkExpiry(value)
            onValueChanged(
                expiryDate.stringValue,
                expiryDate.isValid() && isValid
            )
        },
        visualTransformation = MaskVisualTransformation("##/##"),
        label = getStringOverride(OverridesKeys.TITLE_EXPIRY),
        isDisabled = isDisabled,
        keyboardType = KeyboardType.Number,
        maxLength = 4,
        isRequired = true,
        showRedStarForRequiredFields = showRedStarForRequiredFields,
        testTag = testTag
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreview() {
    ExpiryField(
        modifier = Modifier,
        initialValue = "02/30",
        onValueChanged = { _, _ -> }
    )
}

@Composable
@Preview(showBackground = true)
private fun ExpiryFieldPreviewDisabled() {
    ExpiryField(
        isDisabled = true,
        modifier = Modifier,
        initialValue = "02/30",
        onValueChanged = { _, _ -> }
    )
}