package com.ecommpay.msdk.ui.views.card

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import com.ecommpay.msdk.core.validators.custom.CardHolderNameValidator
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun CardHolderField(
    modifier: Modifier,
    value: String = "",
    isDisabled: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    var cardHolder by remember { mutableStateOf(value) }
    val errorMessage = PaymentActivity.stringResourceManager.getStringByKey("message_card_holder")
        ?: stringResource(R.string.card_holder_invalid_message)
    @Suppress("NAME_SHADOWING")
    CustomTextField(
        modifier = modifier,
        value = cardHolder,
        onTransform = { value -> value.filter { it.isLetter() || it == ' ' } },
        onValueChange = {
            cardHolder = it.uppercase()
            onValueChange.invoke(cardHolder)
        },
        onValidate = {
            when {
                !CardHolderNameValidator().isValid(it) -> errorMessage
                else -> null
            }

        },
        visualTransformation = VisualTransformation.None,
        label = PaymentActivity.stringResourceManager.getStringByKey("title_holder_name")
            ?: stringResource(R.string.holder_name_title),
        isDisabled = isDisabled
    )
}