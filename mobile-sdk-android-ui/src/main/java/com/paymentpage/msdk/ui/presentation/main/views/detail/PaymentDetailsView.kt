package com.paymentpage.msdk.ui.presentation.main.views.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsView(paymentInfo: PaymentInfo) {
    var expandPaymentDetailsState by remember { mutableStateOf(false) }
    ExpandablePaymentDetails(
        onExpandCallback = { expandPaymentDetailsState = true },
        isExpanded = expandPaymentDetailsState
    ) {
        PaymentDetailsContent(
            paymentIdLabel = PaymentActivity.stringResourceManager.getStringByKey("title_payment_id"),
            paymentIdValue = paymentInfo.paymentId,
            paymentDescriptionLabel = PaymentActivity.stringResourceManager.getStringByKey("title_payment_information_description"),
            paymentDescriptionValue = paymentInfo.paymentDescription,
            merchantAddressLabel = "",
            merchantAddressValue = null
        ) {
            expandPaymentDetailsState = false
        }
    }
}

@Composable
fun ExpandablePaymentDetails(
    isExpanded: Boolean = false,
    onExpandCallback: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.animateContentSize(SnapSpec()),
    ) {
        Text(
            modifier = if (!isExpanded) Modifier.clickable { onExpandCallback() } else Modifier,
            text = PaymentActivity.stringResourceManager.getStringByKey("title_payment_information_screen"),
            style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.brand.copy(alpha = if (isExpanded) 0.3f else 1f)),
        )
        if (isExpanded)
            Spacer(modifier = Modifier.size(15.dp))
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .background(
                        color = SDKTheme.colors.panelBackgroundColor,
                        shape = SDKTheme.shapes.radius12
                    )
                    .border(
                        width = 1.dp,
                        color = SDKTheme.colors.borderColor,
                        shape = SDKTheme.shapes.radius12
                    )
                    .padding(20.dp),
                content = content
            )
        }
    }

}