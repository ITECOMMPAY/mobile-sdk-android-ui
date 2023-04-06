package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.presentation.main.payNewCard
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.views.button.SaveButton
import com.paymentpage.msdk.ui.views.card.CardHolderField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.card.panField.PanField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun TokenizeCardPayItem(
    method: UIPaymentMethod.UITokenizeCardPayPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val paymentOptions = LocalPaymentOptions.current
    val tokenizeCustomerFields = remember {
        method.paymentMethod.customerFields.filter { it.isTokenize }
    }

    var scanningResult by remember {
        mutableStateOf<CardScanningActivityContract.Result?>(
            value = null,
            policy = neverEqualPolicy()
        )
    }

    val additionalFields = LocalPaymentOptions.current.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isPanValid by remember { mutableStateOf(method.isValidPan) }
    var isCardHolderValid by remember { mutableStateOf(method.isValidCardHolder) }
    var isExpiryValid by remember { mutableStateOf(method.isValidExpiry) }
    var cardType by remember { mutableStateOf<String?>(null) }

    ExpandablePaymentMethodItem(
        method = method,
        isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                initialValue = method.pan,
                scanningPan = scanningResult?.pan,
                paymentMethod = method.paymentMethod,
                onValueChanged = { value, isValid ->
                    isPanValid = isValid
                    method.pan = value
                    method.isValidPan = isValid
                    scanningResult = null
                },
                onPaymentMethodCardTypeChange = {
                    cardType = it
                },
                onScanningResult = { result ->
                    scanningResult = result
                },
                testTag = TestTagsConstants.PAN_TEXT_FIELD
            )
            Spacer(modifier = Modifier.size(10.dp))
            CardHolderField(
                modifier = Modifier
                    .fillMaxWidth(),
                initialValue = method.cardHolder,
                scanningCardHolder = scanningResult?.cardHolderName,
                onValueChanged = { value, isValid ->
                    isCardHolderValid = isValid
                    method.cardHolder = value
                    method.isValidCardHolder = isValid
                    scanningResult = null
                },
                testTag = TestTagsConstants.CARDHOLDER_TEXT_FIELD
            )
            Spacer(modifier = Modifier.size(10.dp))
            ExpiryField(
                modifier = Modifier
                    .fillMaxWidth(),
                initialValue = method.expiry,
                scanningExpiry = scanningResult?.expiry,
                onValueChanged = { value, isValid ->
                    isExpiryValid = isValid
                    method.expiry = value
                    method.isValidExpiry = isValid
                    scanningResult = null
                },
                testTag = TestTagsConstants.EXPIRY_TEXT_FIELD
            )
            if (
                tokenizeCustomerFields.hasVisibleCustomerFields() &&
                tokenizeCustomerFields.visibleCustomerFields().size <=
                Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
            ) {
                CustomerFields(
                    customerFields = tokenizeCustomerFields,
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues,
                    onCustomerFieldsChanged = { fields, isValid ->
                        method.customerFieldValues = fields
                        isCustomerFieldsValid = isValid
                        method.isCustomerFieldsValid = isCustomerFieldsValid
                    }
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            SaveButton(
                modifier = Modifier
                    .testTag(TestTagsConstants.SAVE_BUTTON),
                method = method,
                customerFields = tokenizeCustomerFields,
                isValid = isPanValid && isCardHolderValid && isExpiryValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    paymentMethodsViewModel.setCurrentMethod(method)
                    mainViewModel.payNewCard(
                        actionType = paymentOptions.actionType,
                        method = method,
                        customerFields = tokenizeCustomerFields
                    )
                }
            )
        }
    }
}
