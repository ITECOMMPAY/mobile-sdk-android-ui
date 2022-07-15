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
    isDisabled: Boolean = false,
    onValueChanged: (String) -> Unit,
) {
    CustomTextField(
        modifier = modifier,
        onFilterValueBefore = { value -> value.filter { it.isLetter() || it == ' ' }.uppercase() },
        onValueChanged = onValueChanged,
        onRequestValidatorMessage = {
            when {
                !CardHolderNameValidator().isValid(it) -> PaymentActivity.stringResourceManager.getStringByKey(
                    "message_card_holder"
                )
                else -> null
            }

        },
        visualTransformation = VisualTransformation.None,
        label = PaymentActivity.stringResourceManager.getStringByKey("title_holder_name"),
        isDisabled = isDisabled,
        isRequired = true
    )
}