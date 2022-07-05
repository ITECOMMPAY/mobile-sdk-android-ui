package com.ecommpay.msdk.ui.presentation.main.views.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsView(paymentOptions: PaymentOptions) {
    var expandPaymentDetailsState by remember { mutableStateOf(false) }
    ExpandablePaymentDetails(
        onExpandCallback = { expandPaymentDetailsState = true },
        isExpanded = expandPaymentDetailsState
    ) {
        PaymentDetailsContent(
            paymentIdLabel = PaymentActivity.stringResourceManager.payment.infoPaymentIdTitle
                ?: stringResource(R.string.payment_id_title),
            paymentIdValue = paymentOptions.paymentInfo?.paymentId,
            paymentDescriptionLabel = PaymentActivity.stringResourceManager.payment.descriptionTitle
                ?: stringResource(R.string.payment_id_title),
            paymentDescriptionValue = paymentOptions.paymentInfo?.paymentDescription,
            merchantAddressLabel = stringResource(R.string.merchant_address_title),
            merchantAddressValue = PaymentActivity.stringResourceManager.merchant.address
        ) {
            expandPaymentDetailsState = false
        }
    }
}

@Composable
fun ExpandablePaymentDetails(
    paymentDetailsLabel: String = stringResource(R.string.payment_details_label),
    isExpanded: Boolean = false,
    onExpandCallback: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.animateContentSize(SnapSpec()),
    ) {
        Text(
            modifier = if (!isExpanded) Modifier.clickable { onExpandCallback() } else Modifier,
            text = paymentDetailsLabel,
            style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.brand.copy(alpha = if (isExpanded) 0.3f else 1f)),
        )
        if (isExpanded)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .background(
                        color = SDKTheme.colors.backgroundExpandedPaymentDetails,
                        shape = SDKTheme.shapes.radius12
                    )
                    .padding(SDKTheme.dimensions.paddingDp20),
                content = content
            )
        }
    }

}