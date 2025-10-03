package com.paymentpage.msdk.ui.views.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.SdkExpiry
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.WalletSaveMode
import com.paymentpage.msdk.core.validators.custom.PanValidator
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.card.panField.PanField

@Composable
internal fun CombinedCardField(
    modifier: Modifier = Modifier,
    scanningResult: CardScanningActivityContract.Result? = null,
    onScanningResultReceived: (CardScanningActivityContract.Result?) -> Unit,
    method: UIPaymentMethod,
    hideScanningCard: Boolean = true,
    onValidationChanged: ((Boolean) -> Unit),
) {
    val panValidator = remember { PanValidator() }

    var isCardEditable by remember { mutableStateOf<Boolean>(method is UIPaymentMethod.UICardPayPaymentMethod) }

    var cardType by remember { mutableStateOf<String?>(null) }

    var cardPanError by remember { mutableStateOf<String?>(null) }
    var cardExpirationError by remember { mutableStateOf<String?>(null) }
    var cardCvvError by remember { mutableStateOf<String?>(null) }

    val cvvLength = if (cardType == "amex") 4 else 3

    val errorMessage = remember(cardPanError, cardExpirationError, cardCvvError) {
        when {
            cardPanError.isNullOrEmpty().not() -> cardPanError
            cardExpirationError.isNullOrEmpty().not() -> cardExpirationError
            cardCvvError.isNullOrEmpty().not() -> cardCvvError
            else -> null
        }
    }

    fun checkFieldValidation() = when (method) {
        is UIPaymentMethod.UICardPayPaymentMethod -> method.isValidPan && method.isValidExpiry && method.isValidCvv
        is UIPaymentMethod.UISavedCardPayPaymentMethod -> method.isValidCvv
        else -> true
    }

    Column(modifier) {
        PanField(
            initialValue = method.pan.orEmpty(),
            scanningPan = scanningResult?.pan,
            paymentMethod = method.paymentMethod,
            isEditable = isCardEditable,
            isMaskEnabled = isCardEditable,
            hideScanningCard = hideScanningCard,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 2.dp,
                bottomEnd = 2.dp
            ),
            onValueChanged = { value, isValid ->
                method.pan = value
                method.isValidPan = isValid
                onScanningResultReceived(null)

                onValidationChanged.invoke(checkFieldValidation())
            },
            onRequestValidatorMessage = {
                cardPanError = when {
                    it.isEmpty() -> getStringOverride(OverridesKeys.MESSAGE_REQUIRED_FIELD)
                    !panValidator.isValid(it) -> getStringOverride(OverridesKeys.MESSAGE_ABOUT_CARD_NUMBER)
                    cardType == null -> null
                    !method.paymentMethod.availableCardTypes.contains(cardType) -> {
                        val regex = Regex("\\[\\[.+]]")
                        val message = regex.replace(
                            getStringOverride(OverridesKeys.MESSAGE_WRONG_CARD_TYPE),
                            cardType?.uppercase() ?: ""
                        )
                        message
                    }

                    else -> null
                }

                null to (cardPanError != null)
            },
            onPaymentMethodCardTypeChange = {
                cardType = it
            },
            onScanningResult = { result ->
                onScanningResultReceived(result)
            },
            testTag = "${
                TestTagsConstants.PREFIX_NEW_CARD
            }${
                TestTagsConstants.PAN_TEXT_FIELD
            }"
        )

        Spacer(modifier = Modifier.size(2.dp))

        Row {
            ExpiryField(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(
                    topStart = 2.dp,
                    topEnd = 2.dp,
                    bottomStart = 12.dp,
                    bottomEnd = 2.dp
                ),
                initialValue = method.expiry,
                scanningExpiry = scanningResult?.expiry,
                errorMessage = errorMessage,
                isDisabled = isCardEditable.not(),
                onValueChanged = { value, isValid ->
                    method.expiry = value
                    method.isValidExpiry = isValid
                    onScanningResultReceived(null)

                    onValidationChanged.invoke(checkFieldValidation())
                },
                onRequestValidatorMessage = {
                    val expiryDate = SdkExpiry(it)

                    cardExpirationError = when {
                        !expiryDate.isValid() || !expiryDate.isMoreThanNow() ->
                            getStringOverride(OverridesKeys.MESSAGE_ABOUT_EXPIRY)

                        else -> null
                    }

                    cardExpirationError
                },
                testTag = "${
                    TestTagsConstants.PREFIX_NEW_CARD
                }${
                    TestTagsConstants.EXPIRY_TEXT_FIELD
                }"
            )
            Spacer(modifier = Modifier.size(2.dp))
            CvvField(
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(
                    topStart = 2.dp,
                    topEnd = 2.dp,
                    bottomStart = 2.dp,
                    bottomEnd = 12.dp
                ),
                initialValue = method.cvv,
                length = cvvLength,
                onValueChanged = { value, isValid ->
                    method.cvv = value
                    method.isValidCvv = isValid

                    onValidationChanged.invoke(checkFieldValidation())
                },
                onRequestValidatorMessage = {
                    cardCvvError = if (it.length != cvvLength)
                        getStringOverride(OverridesKeys.MESSAGE_INVALID_CVV)
                    else
                        null

                    null to (cardCvvError != null)
                },
                testTag = "${
                    TestTagsConstants.PREFIX_NEW_CARD
                }${
                    TestTagsConstants.CVV_TEXT_FIELD
                }"
            )
        }
    }
}

@Preview
@Composable
fun CombinedCardField_Preview() {
    CombinedCardField(
        onScanningResultReceived = {},
        method = UIPaymentMethod.UICardPayPaymentMethod(
            1,
            "Карта",
            paymentMethod = PaymentMethod(
                walletModeAsk = true,
                walletSaveMode = WalletSaveMode.ASK_CUSTOMER_BEFORE_SAVE,
                code = "",
                customerFields = emptyList(),
                availableCardTypes = emptyList(),
            )
        ),
        onValidationChanged = { }
    )
}