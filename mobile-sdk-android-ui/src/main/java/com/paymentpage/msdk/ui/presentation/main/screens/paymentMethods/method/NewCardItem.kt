package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.clip
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
import com.paymentpage.msdk.ui.theme.InterFamily
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.customColor
import com.paymentpage.msdk.ui.views.button.CustomOrConfirmButton
import com.paymentpage.msdk.ui.views.card.CardHolderField
import com.paymentpage.msdk.ui.views.card.CombinedCardField
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

    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCardFieldsValid by remember { mutableStateOf<Boolean>(method.isValidPan && method.isValidExpiry && method.isValidCvv) }
    var isCardHolderValid by remember { mutableStateOf(method.isValidCardHolder) }

    var scanningResult by remember {
        mutableStateOf<CardScanningActivityContract.Result?>(
            value = null,
            policy = neverEqualPolicy()
        )
    }

    val checkedStateContentDescription =
        stringResource(id = R.string.checked_state_content_description)
    val notCheckedStateContentDescription =
        stringResource(id = R.string.not_checked_state_content_description)

    ExpandablePaymentMethodItem(
        method = method,
        isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
        fallbackIcon = painterResource(id = SDKTheme.images.defaultCardLogo),
    ) {
        Column(Modifier.fillMaxWidth()) {
            CombinedCardField(
                modifier = Modifier
                    .fillMaxWidth(),
                method = method,
                hideScanningCard = paymentOptions.hideScanningCards,
                onScanningResultReceived = {
                    scanningResult = it
                },
                onValidationChanged = {
                    isCardFieldsValid = it
                }
            )

            Spacer(modifier = Modifier.size(8.dp))
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
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .size(18.dp)
                            .clip(SDKTheme.shapes.radius4),
                        checked = savedState,
                        //Parent composable fun controls checked state of this checkbox
                        onCheckedChange = null,
                        colors = SDKCheckboxDefaults.colors()
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = getStringOverride(OverridesKeys.TITLE_SAVED_CARDS),
                            fontFamily = SohneBreitFamily,
                            style = SDKTheme.typography.s14Medium
                        )
                        SDKTextWithLink(
                            overrideKey = OverridesKeys.COF_AGREEMENTS,
                            fontFamily = InterFamily,
                            style = SDKTheme.typography.s14Normal
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
                isValid = isCardFieldsValid && isCardHolderValid,
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