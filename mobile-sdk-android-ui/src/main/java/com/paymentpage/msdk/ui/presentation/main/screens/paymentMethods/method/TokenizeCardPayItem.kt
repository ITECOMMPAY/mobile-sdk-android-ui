package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.tokenizeCard
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.needSendWithSaleRequest
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
    val tokenizeCustomerFields = remember {
        method.paymentMethod.customerFields.filter { it.isTokenize }
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
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
        iconColor = ColorFilter.tint(SDKTheme.colors.brand),
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            PanField(
                initialValue = method.pan,
                modifier = Modifier.fillMaxWidth(),
                paymentMethod = method.paymentMethod,
                onValueChanged = { value, isValid ->
                    isPanValid = isValid
                    method.pan = value
                    method.isValidPan = isValid
                },
                onPaymentMethodCardTypeChange = {
                    cardType = it
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            CardHolderField(
                initialValue = method.cardHolder,
                modifier = Modifier.fillMaxWidth(),
                onValueChanged = { value, isValid ->
                    isCardHolderValid = isValid
                    method.cardHolder = value
                    method.isValidCardHolder = isValid
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            ExpiryField(
                modifier = Modifier.fillMaxWidth(),
                initialValue = method.expiry,
                onValueChanged = { value, isValid ->
                    isExpiryValid = isValid
                    method.expiry = value
                    method.isValidExpiry = isValid
                }
            )
            if (tokenizeCustomerFields.hasVisibleCustomerFields() && tokenizeCustomerFields.visibleCustomerFields().size <= Constants.COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
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
                method = method,
                customerFields = tokenizeCustomerFields,
                isValid = isPanValid && isCardHolderValid && isExpiryValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    paymentMethodsViewModel.setCurrentMethod(method)
                    mainViewModel.tokenizeCard(
                        method = method,
                        needSendCustomerFields = tokenizeCustomerFields.needSendWithSaleRequest()
                    )
                }
            )
        }
    }
}