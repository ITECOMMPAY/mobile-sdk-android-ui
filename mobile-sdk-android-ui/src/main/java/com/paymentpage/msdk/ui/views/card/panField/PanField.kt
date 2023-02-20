package com.paymentpage.msdk.ui.views.card.panField

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.card.formatAmex
import com.paymentpage.msdk.ui.utils.card.formatDinnersClub
import com.paymentpage.msdk.ui.utils.card.formatOtherCardNumbers
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName
import com.paymentpage.msdk.ui.views.card.CardScanningItem
import com.paymentpage.msdk.ui.views.common.CustomTextField

@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    initialValue: String,
    scanningPan: String? = null,
    paymentMethod: PaymentMethod,
    onScanningResult: (CardScanningActivityContract.Result) -> Unit,
    onPaymentMethodCardTypeChange: ((String?) -> Unit)? = null,
    onValueChanged: (String, Boolean) -> Unit,
) {
    var card by remember { mutableStateOf<PaymentMethodCard?>(null) }

    Row(modifier = Modifier.fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
        ) {
            val textFieldDefaultHorizontalPadding = 16.dp
            val textFieldDefaultTrailingIconPadding = 48.dp
            val maxInputWidth =
                maxWidth - 2 * textFieldDefaultHorizontalPadding - textFieldDefaultTrailingIconPadding
            //default font size
            var shrunkFontSize = 16.sp
            //calculate width of new font size
            val calculateIntrinsics = @Composable {
                ParagraphIntrinsics(
                    text = initialValue,
                    style = TextStyle(fontSize = shrunkFontSize),
                    density = LocalDensity.current,
                    fontFamilyResolver = createFontFamilyResolver(LocalContext.current)
                )
            }
            var intrinsics = calculateIntrinsics()
            with(LocalDensity.current) {
                while (intrinsics.maxIntrinsicWidth > maxInputWidth.toPx()) {
                    shrunkFontSize *= 0.9
                    intrinsics = calculateIntrinsics()
                }
            }

            CustomTextField(
                modifier = modifier,
                initialValue = initialValue,
                pastedValue = scanningPan,
                isRequired = true,
                textStyle = TextStyle(
                    fontSize = shrunkFontSize
                ),
                singleLine = true,
                keyboardType = KeyboardType.Number,
                onFilterValueBefore = { value -> value.filter { it.isDigit() } },
                maxLength = card?.maxLength ?: 19,
                onValueChanged = { value, isValid ->
                    onValueChanged(value, PanValidator().isValid(value) && isValid)
                },
                onRequestValidatorMessage = {
                    if (!PanValidator().isValid(it))
                        getStringOverride(OverridesKeys.MESSAGE_ABOUT_CARD_NUMBER)
                    else if (card == null) null
                    else if (!paymentMethod.availableCardTypes.contains(card?.code)) {
                        val regex = Regex("\\[\\[.+]]")
                        val message = regex.replace(
                            getStringOverride(OverridesKeys.MESSAGE_WRONG_CARD_TYPE),
                            card?.code?.uppercase() ?: ""
                        )
                        message
                    } else
                        null
                },
                visualTransformation = { number ->
                    val trimmedCardNumber = number.text.replace(" ", "")
                    card = if (trimmedCardNumber.isNotEmpty())
                        paymentMethod.cardTypesManager?.search(trimmedCardNumber)
                    else
                        null
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
                        !initialValue.isNullOrEmpty() -> {
                            val name = "card_type_${card?.code ?: ""}"
                            val drawableId = remember(name) {
                                context.drawableResourceIdFromDrawableName(name)
                            }
                            Image(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(25.dp),
                                painter = painterResource(
                                    id = if (drawableId > 0)
                                        drawableId
                                    else
                                        R.drawable.card_logo
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                colorFilter = if (drawableId == 0)
                                    ColorFilter.tint(SDKTheme.colors.brand)
                                else
                                    null
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
        Spacer(modifier = Modifier.size(10.dp))
        CardScanningItem(
            modifier = Modifier
                .width(TextFieldDefaults.MinHeight)
                .height(TextFieldDefaults.MinHeight),
            onScanningResult = onScanningResult
        )
    }
}
