package com.paymentpage.msdk.ui.views.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCardType
import com.paymentpage.msdk.core.manager.card.CardTypesManager
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.utils.card.formatAmex
import com.paymentpage.msdk.ui.utils.card.formatDinnersClub
import com.paymentpage.msdk.ui.utils.card.formatOtherCardNumbers
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    initialValue: String? = null,
    paymentMethod: PaymentMethod,
    onValueChanged: (String, Boolean) -> Unit,
) {
    var cardType by remember { mutableStateOf<PaymentMethodCard?>(null) }

    CustomTextField(
        initialValue = initialValue,
        isRequired = true,
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        maxLength = 19,
        onValueChanged = { value, isValid ->
            onValueChanged(value, PanValidator().isValid(value) && isValid)
        },
        onRequestValidatorMessage = {
            if (!PanValidator().isValid(it)) PaymentActivity.stringResourceManager.getStringByKey("message_about_card_number") else null
        },
        visualTransformation = { number ->
            val trimmedCardNumber = number.text.replace(" ", "")
            cardType = paymentMethod.cardTypesManager.search(trimmedCardNumber)
            when (cardType?.type) {
                PaymentMethodCardType.AMEX -> formatAmex(number)
                PaymentMethodCardType.DINERS_CLUB -> formatDinnersClub(number)
                else -> formatOtherCardNumbers(number)
            }
        },
        label = PaymentActivity.stringResourceManager.getStringByKey("title_card_number"),
        trailingIcon = {
            val name = "card_type_${cardType?.code ?: ""}"
            val context = LocalContext.current
            val drawableId = remember(name) {
                context.resources.getIdentifier(
                    name,
                    "drawable",
                    context.packageName
                )
            }
            Image(
                modifier = Modifier.padding(15.dp),
                painter = painterResource(id = if (drawableId > 0) drawableId else R.drawable.card_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    )
}

