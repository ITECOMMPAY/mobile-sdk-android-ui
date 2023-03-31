package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.paySavedCard
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.views.button.CustomOrConfirmButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.common.alertDialog.MessageAlertDialog
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun SavedCardItem(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val state = mainViewModel.state.collectAsState().value
    val customerFields = remember { method.paymentMethod.customerFields }
    val paymentOptions = LocalPaymentOptions.current
    val additionalFields = paymentOptions.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCvvValid by remember { mutableStateOf(method.isValidCvv) }
    val isDeleteCardLoading = state.isDeleteCardLoading ?: false
    var deleteCardAlertDialogState by remember { mutableStateOf(false) }
    val token = paymentOptions.paymentInfo.token
    ExpandablePaymentMethodItem(
        method = method,
        isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen,
        fallbackIcon = painterResource(SDKTheme.images.defaultCardLogo),
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ExpiryField(
                    modifier = Modifier
                        .weight(1f)
                        .testTag(
                            "${
                                TestTagsConstants.PREFIX_SAVE_CARD
                            }${
                                TestTagsConstants.EXPIRY_TEXT_FIELD
                            }"
                        ),
                    initialValue = method.savedAccount.cardExpiry?.stringValue ?: "",
                    isDisabled = true,
                    showRedStarForRequiredFields = false,
                    onValueChanged = { _, _ ->
                        //we can't change value and isValid always equals true
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                CvvField(
                    modifier = Modifier
                        .weight(1f)
                        .testTag(
                            "${
                                TestTagsConstants.PREFIX_SAVE_CARD
                            }${
                                TestTagsConstants.CVV_TEXT_FIELD
                            }"
                        ),
                    initialValue = method.cvv,
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
                        method.isValidCvv = isValid
                    },
                    cardType = method.savedAccount.cardType,
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
                        isCustomerFieldsValid = isValid
                        method.customerFieldValues = fields
                        method.isCustomerFieldsValid = isValid
                    }
                )
            }
            Spacer(modifier = Modifier.size(22.dp))
            CustomOrConfirmButton(
                actionType = paymentOptions.actionType,
                method = method,
                customerFields = customerFields,
                isValid = isCvvValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    paymentMethodsViewModel.setCurrentMethod(method)
                    mainViewModel.paySavedCard(
                        actionType = paymentOptions.actionType,
                        token = token,
                        method = method,
                        recipientInfo = paymentOptions.recipientInfo,
                        customerFields = customerFields
                    )
                }
            )
            if (token == null) {
                Spacer(modifier = Modifier.size(15.dp))
                if (!isDeleteCardLoading)
                    Text(
                        modifier = Modifier
                            .clickable {
                                deleteCardAlertDialogState = true
                            }
                            .testTag(TestTagsConstants.DELETE_CARD_BUTTON),
                        text = getStringOverride(OverridesKeys.BUTTON_DELETE),
                        style = SDKTheme.typography.s14Normal.copy(
                            color =
                            if (!SDKTheme.colors.isDarkTheme)
                                SDKTheme.colors.grey
                            else
                                SDKTheme.colors.link,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                else {
                    CircularProgressIndicator(
                        color = SDKTheme.colors.primary
                    )
                }
                if (deleteCardAlertDialogState) {
                    MessageAlertDialog(
                        message = getStringOverride(OverridesKeys.MESSAGE_DELETE_CARD_SINGLE),
                        dismissButtonText = getStringOverride(OverridesKeys.BUTTON_CANCEL),
                        onConfirmButtonClick = {
                            deleteCardAlertDialogState = false
                            paymentMethodsViewModel.deleteSavedCard(method = method)
                        },
                        onDismissButtonClick = { deleteCardAlertDialogState = false },
                        confirmButtonText = getStringOverride(OverridesKeys.BUTTON_DELETE),
                        brandColor = paymentOptions.brandColor
                    )
                }
            }
        }
    }
}
