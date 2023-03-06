package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.presentation.main.payNewCard
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.customColor
import com.paymentpage.msdk.ui.views.button.CustomOrConfirmButton
import com.paymentpage.msdk.ui.views.card.CardHolderField
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.card.panField.PanField
import com.paymentpage.msdk.ui.views.common.SDKTextWithLink
import com.paymentpage.msdk.ui.views.common.checkbox.SDKCheckboxDefaults
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun NewCardItem(
    method: UIPaymentMethod.UICardPayPaymentMethod,
    actionType: SDKActionType,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val isShowSaveCardCheckbox = method.paymentMethod.walletModeAsk
    val paymentOptions = LocalPaymentOptions.current
    val additionalFields = paymentOptions.additionalFields
    val savedState = remember { mutableStateOf(method.saveCard) }

    var scanningResult by remember {
        mutableStateOf<CardScanningActivityContract.Result?>(
            value = null,
            policy = neverEqualPolicy()
        )
    }

    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCvvValid by remember { mutableStateOf(method.isValidCvv) }
    var isPanValid by remember { mutableStateOf(method.isValidPan) }
    var isCardHolderValid by remember { mutableStateOf(method.isValidCardHolder) }
    var isExpiryValid by remember { mutableStateOf(method.isValidExpiry) }
    var cardType by remember { mutableStateOf<String?>(null) }

    ExpandablePaymentMethodItem(
        method = method,
        isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
        fallbackIcon = painterResource(id = SDKTheme.images.defaultCardLogo),
        //default card icon color
        iconColor = ColorFilter.tint(
            color = customColor(paymentOptions.brandColor)
        ),
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
            )
            Spacer(modifier = Modifier.size(10.dp))
            CardHolderField(
                modifier = Modifier.fillMaxWidth(),
                initialValue = method.cardHolder,
                scanningCardHolder = scanningResult?.cardHolderName,
                onValueChanged = { value, isValid ->
                    isCardHolderValid = isValid
                    method.cardHolder = value
                    method.isValidCardHolder = isValid
                    scanningResult = null
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    initialValue = method.expiry,
                    scanningExpiry = scanningResult?.expiry,
                    onValueChanged = { value, isValid ->
                        isExpiryValid = isValid
                        method.expiry = value
                        method.isValidExpiry = isValid
                        scanningResult = null
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                CvvField(
                    initialValue = method.cvv,
                    modifier = Modifier.weight(1f),
                    cardType = cardType,
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
                        method.isValidCvv = isValid
                    }
                )
            }

            if (customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                CustomerFields(
                    customerFields = customerFields,
                    additionalFields = additionalFields,
                    customerFieldValues = method.customerFieldValues,
                    onCustomerFieldsChanged = { fields, isValid ->
                        method.customerFieldValues = fields
                        isCustomerFieldsValid = isValid
                        method.isCustomerFieldsValid = isCustomerFieldsValid
                    }
                )
            }
            //if action type is verify we should not show save card checkbox
            //if walletModeAsk != true (ask customer before save) we should not show save card checkbox
            if (actionType != SDKActionType.Verify && isShowSaveCardCheckbox) {
                Spacer(modifier = Modifier.size(22.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            savedState.value = !savedState.value
                        },
                    verticalAlignment = Alignment.Top
                ) {
                    Checkbox(
                        modifier = Modifier.size(25.dp),
                        checked = savedState.value,
                        onCheckedChange = {
                            savedState.value = it
                            method.saveCard = it
                        },
                        colors = SDKCheckboxDefaults.colors()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = getStringOverride(OverridesKeys.TITLE_SAVED_CARDS),
                            style = SDKTheme.typography.s16Normal
                        )
                        SDKTextWithLink(
                            overrideKey = OverridesKeys.COF_AGREEMENTS,
                            style = SDKTheme.typography.s12Light
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            CustomOrConfirmButton(
                actionType = paymentOptions.actionType,
                method = method,
                customerFields = customerFields,
                isValid = isCvvValid && isPanValid && isCardHolderValid && isExpiryValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    paymentMethodsViewModel.setCurrentMethod(method)
                    mainViewModel.payNewCard(
                        actionType = paymentOptions.actionType,
                        method = method,
                        recipientInfo = paymentOptions.recipientInfo,
                        customerFields = customerFields
                    )
                }
            )
        }
    }
}