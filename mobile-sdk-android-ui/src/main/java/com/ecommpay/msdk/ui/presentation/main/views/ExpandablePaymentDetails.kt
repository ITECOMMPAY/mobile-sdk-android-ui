package com.ecommpay.msdk.ui.presentation.main.views

import androidx.compose.runtime.*
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions

@Composable
fun ExpandablePaymentDetails(paymentOptions: PaymentOptions) {
    var expandPaymentDetailsState by remember { mutableStateOf(false) }
    PaymentDetails(
        onExpandCallback = {
            expandPaymentDetailsState = true
        },
        isExpanded = expandPaymentDetailsState
    ) {
        PaymentDetailsContent(
            paymentIDLabel = PaymentActivity.stringResourceManager.payment.infoPaymentIdTitle,
            paymentIDValue = paymentOptions.paymentId,
            paymentDescriptionLabel = PaymentActivity.stringResourceManager.payment.infoTitle,
            paymentDescriptionValue = paymentOptions.paymentDescription,
            paymentAddressLabel = null,
            paymentAddressValue = null) {
            expandPaymentDetailsState = false
        }
    }
}