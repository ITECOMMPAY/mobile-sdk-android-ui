package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.WalletSaveMode
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
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
    val walletSaveMode = method.paymentMethod.walletSaveMode
    val paymentOptions = LocalPaymentOptions.current
    val additionalFields = paymentOptions.additionalFields
    var savedState by remember { mutableStateOf(method.saveCard) }

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

    val checkedStateContentDescription =
        stringResource(id = R.string.checked_state_content_description)
    val notCheckedStateContentDescription =
        stringResource(id = R.string.not_checked_state_content_description)

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
                testTag = "${
                    TestTagsConstants.PREFIX_NEW_CARD
                }${
                    TestTagsConstants.PAN_TEXT_FIELD
                }"
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
                testTag = "${
                    TestTagsConstants.PREFIX_NEW_CARD
                }${
                    TestTagsConstants.CARDHOLDER_TEXT_FIELD
                }"
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row {
                ExpiryField(
                    modifier = Modifier
                        .weight(1f),
                    initialValue = method.expiry,
                    scanningExpiry = scanningResult?.expiry,
                    onValueChanged = { value, isValid ->
                        isExpiryValid = isValid
                        method.expiry = value
                        method.isValidExpiry = isValid
                        scanningResult = null
                    },
                    testTag = "${
                        TestTagsConstants.PREFIX_NEW_CARD
                    }${
                        TestTagsConstants.EXPIRY_TEXT_FIELD
                    }"
                )
                Spacer(modifier = Modifier.size(10.dp))
                CvvField(
                    modifier = Modifier
                        .weight(1f),
                    initialValue = method.cvv,
                    cardType = cardType,
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
                        method.isValidCvv = isValid
                    },
                    testTag = "${
                        TestTagsConstants.PREFIX_NEW_CARD
                    }${
                        TestTagsConstants.CVV_TEXT_FIELD
                    }"
                )
            }

            if (
                customerFields.hasVisibleCustomerFields() &&
                customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS
            ) {
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

            /*
            if walletSaveMode != WalletSaveMode.ASK_CUSTOMER_BEFORE_SAVE
            we should not SHOW save card checkbox
            */
            if (actionType != SDKActionType.Verify
                && walletSaveMode == WalletSaveMode.ASK_CUSTOMER_BEFORE_SAVE
            ) {
                Spacer(modifier = Modifier.size(22.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            role = Role.Checkbox,
                            onClick = {
                                savedState = !savedState
                                method.saveCard = savedState
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        )
                        .semantics(mergeDescendants = true) {
                            stateDescription = if (savedState)
                                checkedStateContentDescription
                            else
                                notCheckedStateContentDescription
                        },
                    verticalAlignment = Alignment.Top
                ) {
                    Checkbox(
                        modifier = Modifier.size(25.dp),
                        checked = savedState,
                        //Parent composable fun controls checked state of this checkbox
                        onCheckedChange = null,
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
                testTagPrefix = TestTagsConstants.PREFIX_NEW_CARD,
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
                        customerFields = customerFields,
                        storedCardType = paymentOptions.storedCardType
                    )
                }
            )
        }
    }
}