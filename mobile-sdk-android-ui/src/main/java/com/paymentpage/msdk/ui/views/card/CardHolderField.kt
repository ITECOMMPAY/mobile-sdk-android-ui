package com.paymentpage.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.paymentpage.msdk.core.validators.custom.CardHolderNameValidator
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun CardHolderField(
    modifier: Modifier,
    initialValue: String? = null,
    isDisabled: Boolean = false,
    onValueChanged: (String, Boolean) -> Unit,
) {
    CustomTextField(
        initialValue = initialValue,
        modifier = modifier,
        onFilterValueBefore = { value -> value.filter { it.isLetter() || it == ' ' || it == '.' || it == '-' || it == '\'' }.uppercase()},
        onValueChanged = { value, isValid ->
            onValueChanged(value, isValid && CardHolderNameValidator().isValid(value))
        },
        onRequestValidatorMessage = {
            when {
                !CardHolderNameValidator().isValid(it) ->
                    PaymentActivity.stringResourceManager.getStringByKey("message_card_holder")
                else -> null
            }

        },
        visualTransformation = VisualTransformation.None,
        label = PaymentActivity.stringResourceManager.getStringByKey("title_holder_name"),
        isDisabled = isDisabled,
        isRequired = true
    )
}