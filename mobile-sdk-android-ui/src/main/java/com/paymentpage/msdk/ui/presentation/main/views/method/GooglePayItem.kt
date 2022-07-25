package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.GooglePayButton
import com.paymentpage.msdk.ui.views.common.CustomButton
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun GooglePayItem(
    method: UiPaymentMethod.UIGooglePayPaymentMethod,
) {
    val mainViewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalAdditionalFields.current
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }

    var isCustomerFieldsValid by remember { mutableStateOf( method.isCustomerFieldsValid) }

    if (visibleCustomerFields.isEmpty()) {
        CustomButton(
            modifier = Modifier.height(50.dp),
            isEnabled = true,
            color = Color.Black,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.googlepay_button_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            },
            onClick = {

            }
        )
    } else {
        ExpandablePaymentMethodItem(
            method = method,
            headerBackgroundColor = SDKTheme.colors.backgroundColor,
            fallbackIcon = painterResource(id = SDKTheme.images.googlePayMethodResId),
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            CustomerFields(
                visibleCustomerFields = visibleCustomerFields,
                additionalFields = additionalFields,
                customerFieldValues = method.customerFieldValues,
                onCustomerFieldsChanged = { fields, isValid ->
                    method.customerFieldValues = fields
                    isCustomerFieldsValid = isValid
                    method.isCustomerFieldsValid = isCustomerFieldsValid
                }
            )
            Spacer(modifier = Modifier.size(22.dp))
            GooglePayButton( isEnabled = isCustomerFieldsValid)
        }
    }
}