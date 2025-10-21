package com.paymentpage.msdk.ui.views.card.panField

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.card.formatAmex
import com.paymentpage.msdk.ui.utils.card.formatDinnersClub
import com.paymentpage.msdk.ui.utils.card.formatOtherCardNumbers
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.card.CardScanningItem
import com.paymentpage.msdk.ui.views.common.CustomTextField

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun PanField(
    modifier: Modifier = Modifier,
    initialValue: String,
    scanningPan: String? = null,
    shape: Shape = SDKTheme.shapes.radius16,
    paymentMethod: PaymentMethod,
    onScanningResult: (CardScanningActivityContract.Result) -> Unit,
    onPaymentMethodCardTypeChange: ((String?) -> Unit)? = null,
    isEditable: Boolean = true,
    hideScanningCard: Boolean = true,
    isMaskEnabled: Boolean = true,
    testTag: String? = null,
    onValueChanged: (String, Boolean) -> Unit,
    onRequestValidatorMessage: ((String) -> Pair<String?, Boolean>?),
) {
    val panValidator = remember { PanValidator() }

    var card by remember { mutableStateOf<PaymentMethodCard?>(null) }
    var isError by remember { mutableStateOf(false) }

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
                shape = shape,
                isError = isError,
                isDisabled = isEditable.not(),
                keyboardType = KeyboardType.Number,
                onFilterValueBefore = { value -> value.filter { it.isDigit() } },
                maxLength = card?.maxLength ?: 19,
                onFocusChanged = { isFocused ->
                    if (isFocused) { isError = false }
                },
                onValueChanged = { value, isValid ->
                    onValueChanged(value, panValidator.isValid(value) && isValid)
                },
                onRequestValidatorMessage = { text ->
                    val validatorResponse = onRequestValidatorMessage.invoke(text)
                    isError = validatorResponse?.second ?: false

                    validatorResponse?.first
                },
                visualTransformation = { number ->
                    if (isMaskEnabled.not()) {
                        return@CustomTextField TransformedText(number, OffsetMapping.Identity)
                    }

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
                testTag = testTag
            )
        }

        //TODO("CardIO. Remove when card scanning will be implemented again")
        if (!hideScanningCard && false) {
            Spacer(modifier = Modifier.size(10.dp))
            CardScanningItem(
                modifier = Modifier
                    .width(TextFieldDefaults.MinHeight)
                    .height(TextFieldDefaults.MinHeight)
                    .semantics {
                        invisibleToUser()
                    }
                    .testTag(TestTagsConstants.CARD_SCANNING_BUTTON),
                onScanningResult = onScanningResult
            )
        }
    }
}
