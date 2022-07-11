package com.ecommpay.msdk.ui.views.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethodCard
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethodCardType
import com.ecommpay.msdk.core.manager.card.CardTypesManager
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.utils.card.formatAmex
import com.ecommpay.msdk.ui.utils.card.formatDinnersClub
import com.ecommpay.msdk.ui.utils.card.formatOtherCardNumbers
import com.ecommpay.msdk.ui.views.common.CustomTextField

@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    cardTypes: List<PaymentMethodCard>,
    onValueChange: (String) -> Unit,
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardType by remember { mutableStateOf<PaymentMethodCard?>(null) }
    val cardTypesManager = CardTypesManager(cardTypes)

    CustomTextField(
        modifier = modifier,
        value = cardNumber,
        keyboardType = KeyboardType.Number,
        onTransform = { value -> value.filter { it.isDigit() } },
        onValueChange = { value ->
            cardNumber =
                if (value.length >= 19) value.substring(0..18) else value
            onValueChange.invoke(cardNumber)
        },
        visualTransformation = { number ->
            val trimmedCardNumber = number.text.replace(" ", "")
            cardType = cardTypesManager.search(trimmedCardNumber)
            when (cardType?.type) {
                PaymentMethodCardType.AMEX -> formatAmex(number)
                PaymentMethodCardType.DINERS_CLUB -> formatDinnersClub(number)
                else -> formatOtherCardNumbers(number)
            }
        },
        label = PaymentActivity.stringResourceManager.getStringByKey("title_card_number")
            ?: stringResource(R.string.card_number_title),
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

@Composable
@Preview(showBackground = true)
private fun PanFieldPreview() {
    PanField(
        modifier = Modifier,
        cardTypes = emptyList(),
        onValueChange = {}
    )
}

