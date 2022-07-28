package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleSavedCard
import com.paymentpage.msdk.ui.presentation.main.views.COUNT_OF_VISIBLE_CUSTOMER_FIELDS
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.hasVisibleCustomerFields
import com.paymentpage.msdk.ui.utils.extensions.core.visibleCustomerFields
import com.paymentpage.msdk.ui.views.button.PayOrConfirmButton
import com.paymentpage.msdk.ui.views.card.CvvField
import com.paymentpage.msdk.ui.views.card.ExpiryField
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun SavedCardItem(
    method: UIPaymentMethod.UISavedCardPayPaymentMethod,
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }
    var isCvvValid by remember { mutableStateOf(method.isValidCvv) }

    ExpandablePaymentMethodItem(
        method = method,
        headerBackgroundColor = SDKTheme.colors.backgroundColor,
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
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
                    }
                )
            }
            if (customerFields.hasVisibleCustomerFields() && customerFields.visibleCustomerFields().size <= COUNT_OF_VISIBLE_CUSTOMER_FIELDS) {
                CustomerFields(
                    visibleCustomerFields = customerFields.visibleCustomerFields(),
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
                isValidCvv = isCvvValid,
                isValidCustomerFields = isCustomerFieldsValid,
                onClickButton = {
                    viewModel.saleSavedCard(
                        method = method,
                        allCustomerFields = customerFields,
                        additionalFields = additionalFields
                    )
                }
            )
        }
    }
}