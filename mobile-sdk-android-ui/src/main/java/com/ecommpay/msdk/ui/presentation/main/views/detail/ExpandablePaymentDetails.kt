package com.ecommpay.msdk.ui.presentation.main.views.detail

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R

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
            paymentIdLabel = PaymentActivity.stringResourceManager.payment.infoPaymentIdTitle
                ?: stringResource(R.string.payment_id_title),
            paymentIdValue = paymentOptions.paymentId,
            paymentDescriptionLabel = PaymentActivity.stringResourceManager.payment.descriptionTitle
                ?: stringResource(R.string.payment_id_title),
            paymentDescriptionValue = paymentOptions.paymentDescription,
            merchantAddressLabel = stringResource(R.string.merchant_address_title),
            merchantAddressValue = PaymentActivity.stringResourceManager.merchant.address
        ) {
            expandPaymentDetailsState = false
        }
    }
}