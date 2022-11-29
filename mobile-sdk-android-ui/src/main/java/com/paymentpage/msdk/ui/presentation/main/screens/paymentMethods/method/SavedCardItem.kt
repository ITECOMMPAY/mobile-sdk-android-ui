package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.base.Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.deleteSavedCard
import com.paymentpage.msdk.ui.presentation.main.saleSavedCard
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName
import com.paymentpage.msdk.ui.views.button.PayOrConfirmButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.common.alertDialog.MessageAlertDialog
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun SavedCardItem(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
) {
    val viewModel = LocalMainViewModel.current
    val state = viewModel.state.collectAsState().value
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCvvValid by remember { mutableStateOf(method.isValidCvv) }
    val isDeleteCardLoading = state.isDeleteCardLoading ?: false
    val context = LocalContext.current
    val name = "card_type_${method.savedAccount.cardType?.lowercase()}"
    val drawableId = remember(name) {
        context.drawableResourceIdFromDrawableName(name)
    }
    var deleteCardAlertDialogState by remember { mutableStateOf(false) }
    val isSaleWithToken = LocalPaymentOptions.current.paymentInfo.token != null
    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = if (drawableId > 0) drawableId else SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                ExpiryField(
                    modifier = Modifier.weight(1f),
                    initialValue = method.savedAccount.cardExpiry?.stringValue ?: "",
                    isDisabled = true,
                    onValueChanged = { _, _ ->
                        //we can't change value and isValid always equals true
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                CvvField(
                    initialValue = method.cvv,
                    modifier = Modifier.weight(1f),
                    onValueChanged = { value, isValid ->
                        isCvvValid = isValid
                        method.cvv = value
                        method.isValidCvv = isValid
                    },
                    cardType = method.savedAccount.cardType
                )
            }
            if (customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                CustomerFields(
                    customerFields = customerFields.visibleCustomerFields(),
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
            PayOrConfirmButton(
                method = method,
                customerFields = customerFields,
                isValid = isCvvValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    viewModel.saleSavedCard(method = method)
                }
            )
            if (!isSaleWithToken) {
                Spacer(modifier = Modifier.size(15.dp))
                if (!isDeleteCardLoading)
                    Text(
                        modifier = Modifier.clickable {
                            deleteCardAlertDialogState = true
                        },
                        text = getStringOverride(OverridesKeys.BUTTON_DELETE),
                        style = SDKTheme.typography.s14Normal.copy(
                            color = SDKTheme.colors.secondaryTextColor,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                else {
                    CircularProgressIndicator(
                        color = SDKTheme.colors.brand
                    )
                }
                if (deleteCardAlertDialogState) {
                    MessageAlertDialog(
                        message = { Text(text = getStringOverride(OverridesKeys.MESSAGE_DELETE_CARD_SINGLE)) },
                        dismissButtonText = getStringOverride(OverridesKeys.BUTTON_CANCEL),
                        onConfirmButtonClick = {
                            deleteCardAlertDialogState = false
                            viewModel.deleteSavedCard(method = method)
                        },
                        onDismissButtonClick = { deleteCardAlertDialogState = false },
                        confirmButtonText = getStringOverride(OverridesKeys.BUTTON_DELETE)
                    )
                }
            }
        }
    }
}
