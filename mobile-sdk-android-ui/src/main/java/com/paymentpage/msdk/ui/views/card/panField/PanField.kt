package com.paymentpage.msdk.ui.views.card.panField

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCardType
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.utils.card.formatAmex
import com.paymentpage.msdk.ui.utils.card.formatDinnersClub
import com.paymentpage.msdk.ui.utils.card.formatOtherCardNumbers
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    initialValue: String? = null,
    paymentMethod: PaymentMethod,
    onPaymentMethodCardTypeChange: ((PaymentMethodCardType?) -> Unit)? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    var card by remember { mutableStateOf<PaymentMethodCard?>(null) }
    var isFocused by remember { mutableStateOf(false) }
    var currentPanFieldValue by remember { mutableStateOf(initialValue) }
    CustomTextField(
        initialValue = initialValue,
        isRequired = true,
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        maxLength = 19,
        onValueChanged = { value, isValid ->
            onValueChanged(value, PanValidator().isValid(value) && isValid)
            currentPanFieldValue = value
        },
        onRequestValidatorMessage = {
            if (!PanValidator().isValid(it))
                PaymentActivity.stringResourceManager.getStringByKey("message_about_card_number")
            else if (!paymentMethod.availableCardTypes.contains(card?.type)) {
                val regex = Regex("\\[\\[.+]]")
                val message = regex.replace(
                    PaymentActivity.stringResourceManager.getStringByKey("message_wrong_card_type"),
                    card?.type?.value?.uppercase() ?: ""
                )
                message
            } else null
        },
        visualTransformation = { number ->
            val trimmedCardNumber = number.text.replace(" ", "")
            card = paymentMethod.cardTypesManager.search(trimmedCardNumber)
            if (onPaymentMethodCardTypeChange != null) {
                onPaymentMethodCardTypeChange(card?.type)
            }
            when (card?.type) {
                PaymentMethodCardType.AMEX -> formatAmex(number)
                PaymentMethodCardType.DINERS_CLUB -> formatDinnersClub(number)
                else -> formatOtherCardNumbers(number)
            }
        },
        label = PaymentActivity.stringResourceManager.getStringByKey("title_card_number"),
        onFocusChanged = { focusValue ->
            isFocused = focusValue
        },
        trailingIcon = {
            val context = LocalContext.current
            var startIndex by remember { mutableStateOf(0) }
            when {
                !currentPanFieldValue.isNullOrEmpty() -> {
                    val name = "card_type_${card?.code ?: ""}"
                    val drawableId = remember(name) {
                        context.drawableResourceIdFromDrawableName(name)
                    }
                    Image(
                        modifier = Modifier.padding(15.dp),
                        painter = painterResource(id = if (drawableId > 0) drawableId else R.drawable.card_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                isFocused -> { null }
                else -> {
                    ChangingCardTypeItems(
                        firstCardType = paymentMethod.allCardTypes.find { it.type == PaymentMethodCardType.VISA },
                        secondCardType = paymentMethod.allCardTypes.find { it.type == PaymentMethodCardType.MASTER_5 },
                        startIndex = startIndex, //saving current showing card type
                        onCurrentIndexChanged = { currentIndex ->
                            startIndex = currentIndex
                        }
                    )
                }
            }
        }
    )
}

