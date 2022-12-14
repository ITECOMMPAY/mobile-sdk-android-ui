package com.paymentpage.msdk.ui.views.card.panField

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.card.formatAmex
import com.paymentpage.msdk.ui.utils.card.formatDinnersClub
import com.paymentpage.msdk.ui.utils.card.formatOtherCardNumbers
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    initialValue: String? = null,
    paymentMethod: PaymentMethod,
    onPaymentMethodCardTypeChange: ((String?) -> Unit)? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    var card by remember { mutableStateOf<PaymentMethodCard?>(null) }
    println(card?.maxLength)
    var currentPanFieldValue by remember { mutableStateOf(initialValue) }
    CustomTextField(
        initialValue = initialValue,
        isRequired = true,
        modifier = modifier,
        keyboardType = KeyboardType.Number,
        onFilterValueBefore = { value -> value.filter { it.isDigit() } },
        maxLength = card?.maxLength ?: 19,
        onValueChanged = { value, isValid ->
            onValueChanged(value, PanValidator().isValid(value) && isValid)
            currentPanFieldValue = value
        },
        onRequestValidatorMessage = {
            if (!PanValidator().isValid(it))
                getStringOverride(OverridesKeys.MESSAGE_ABOUT_CARD_NUMBER)
            else if (!paymentMethod.availableCardTypes.contains(card?.code)) {
                val regex = Regex("\\[\\[.+]]")
                val message = regex.replace(
                    getStringOverride(OverridesKeys.MESSAGE_WRONG_CARD_TYPE),
                    card?.code?.uppercase() ?: ""
                )
                message
            } else null
        },
        visualTransformation = { number ->
            val trimmedCardNumber = number.text.replace(" ", "")
            card = paymentMethod.cardTypesManager?.search(trimmedCardNumber)
            if (onPaymentMethodCardTypeChange != null) {
                onPaymentMethodCardTypeChange(card?.code)
            }
            when (card?.code) {
                Constants.AMEX_CARD_TYPE_NAME -> formatAmex(number)
                Constants.DINERS_CLUB_CARD_TYPE_NAME -> formatDinnersClub(number)
                else -> formatOtherCardNumbers(number)
            }
        },
        label = getStringOverride(OverridesKeys.TITLE_CARD_NUMBER),
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
                        modifier = Modifier
                            .padding(5.dp)
                            .size(25.dp),
                        painter = painterResource(id = if (drawableId > 0) drawableId else R.drawable.card_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        colorFilter = if (drawableId == 0) ColorFilter.tint(SDKTheme.colors.brand) else null
                    )
                }
                else -> {
                    if (paymentMethod.availableCardTypes.isNotEmpty())
                        ChangingCardTypeItems(
                            cardTypes = paymentMethod.availableCardTypes,
                            startIndex = startIndex, //saving current showing card type
                            onCurrentIndexChanged = { currentIndex ->
                                startIndex = currentIndex
                            }
                        )
                    else
                        Image(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(25.dp),
                            painter = painterResource(id = R.drawable.card_logo),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(SDKTheme.colors.brand)
                        )
                }
            }
        }
    )
}

