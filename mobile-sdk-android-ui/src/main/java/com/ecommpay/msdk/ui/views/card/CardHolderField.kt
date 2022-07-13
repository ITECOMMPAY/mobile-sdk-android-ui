package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.ecommpay.msdk.core.validators.custom.CardHolderNameValidator
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun CardHolderField(
    modifier: Modifier,
    isDisabled: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    val errorMessage = PaymentActivity.stringResourceManager.getStringByKey("message_card_holder")
    @Suppress("NAME_SHADOWING")
    CustomTextField(
        modifier = modifier,
        onFilterValueBefore = { value -> value.filter { it.isLetter() || it == ' ' } },
        onValueChange = {
            onValueChange.invoke(it.uppercase())
        },
        onValidate = {
            when {
                !CardHolderNameValidator().isValid(it) -> errorMessage
                else -> null
            }

        },
        visualTransformation = VisualTransformation.None,
        label = PaymentActivity.stringResourceManager.getStringByKey("title_holder_name"),
        isDisabled = isDisabled
    )
}