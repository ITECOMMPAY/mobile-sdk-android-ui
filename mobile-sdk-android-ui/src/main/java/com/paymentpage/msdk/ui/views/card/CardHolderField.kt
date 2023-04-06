package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.paymentpage.msdk.core.validators.custom.CardHolderNameValidator
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun CardHolderField(
    modifier: Modifier,
    initialValue: String? = null,
    scanningCardHolder: String? = null,
    isDisabled: Boolean = false,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    CustomTextField(
        initialValue = initialValue,
        pastedValue = scanningCardHolder,
        modifier = modifier,
        onFilterValueBefore = { value ->
            value.filter { it.isLetter() || it == ' ' || it == '.' || it == '-' || it == '\'' }
                .uppercase()
        },
        onValueChanged = { value, isValid ->
            onValueChanged(value, isValid && CardHolderNameValidator().isValid(value))
        },
        onRequestValidatorMessage = {
            when {
                !CardHolderNameValidator().isValid(it) ->
                    getStringOverride(OverridesKeys.MESSAGE_CARD_HOLDER)
                else -> null
            }
        },
        visualTransformation = VisualTransformation.None,
        label = getStringOverride(OverridesKeys.TITLE_HOLDER_NAME),
        isDisabled = isDisabled,
        isRequired = true,
        testTag = testTag
    )
}