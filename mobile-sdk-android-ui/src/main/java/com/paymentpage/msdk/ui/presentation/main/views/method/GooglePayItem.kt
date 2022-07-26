package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleGooglePay
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.merge
import com.paymentpage.msdk.ui.views.button.GooglePayButton
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun GooglePayItem(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
) {
    val paymentOptions = LocalPaymentOptions.current
    val mainViewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    val merchantId = LocalPaymentOptions.current.merchantId

    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }

    if (visibleCustomerFields.isEmpty()) {
        GooglePayButton(
            isEnabled = true,
            onComplete = { result ->
                //TODO need handle error from gpay
                result.token?.let {
                    mainViewModel.saleGooglePay(
                        method = method,
                        merchantId = merchantId,
                        token = it,
                        environment = paymentOptions.merchantEnvironment
                    )
                }
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
            GooglePayButton(
                isEnabled = isCustomerFieldsValid,
                onComplete = { result ->
                    //TODO need handle error from gpay
                    result.token?.let {
                        mainViewModel.saleGooglePay(
                            method = method,
                            merchantId = merchantId,
                            token = it,
                            environment = paymentOptions.merchantEnvironment,
                            customerFields = customerFields.merge(
                                changedFields = method.customerFieldValues,
                                additionalFields = additionalFields
                            )
                        )
                    }

                }
            )
        }
    }
}