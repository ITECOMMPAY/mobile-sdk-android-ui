package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.googlePay.GooglePayActivityContract
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.saleGooglePay
import com.paymentpage.msdk.ui.presentation.main.views.method.expandable.ExpandablePaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.GooglePayButton
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields


@Composable
internal fun GooglePayItem(
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val paymentOptions = LocalPaymentOptions.current
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod.customerFields }
    val additionalFields = LocalPaymentOptions.current.additionalFields
    val visibleCustomerFields = remember { customerFields.filter { !it.isHidden } }
    val merchantId = LocalPaymentOptions.current.merchantId

    var isCustomerFieldsValid by remember { mutableStateOf(method.isCustomerFieldsValid) }

    val handle: (GooglePayActivityContract.Result) -> Unit = { result ->
        if (!result.errorMessage.isNullOrEmpty()) {
            onError(
                ErrorResult(code = ErrorCode.UNKNOWN, message = result.errorMessage),
                false
            )
        } else
            result.token?.let {
                viewModel.saleGooglePay(
                    method = method,
                    merchantId = merchantId,
                    token = it,
                    environment = paymentOptions.merchantEnvironment,
                    allCustomerFields = customerFields,
                    additionalFields = additionalFields
                )
            }
    }

    if (visibleCustomerFields.isEmpty()) {
        GooglePayButton(
            isEnabled = true,
            onComplete = handle
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
                    method.isCustomerFieldsValid = isValid
                }
            )
            Spacer(modifier = Modifier.size(22.dp))
            GooglePayButton(
                isEnabled = isCustomerFieldsValid,
                onComplete = handle
            )
        }
    }
}