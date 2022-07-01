package com.ecommpay.msdk.ui.presentation.main.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetails(
    paymentDetailsLabel: String = stringResource(R.string.payment_details_label),
    isExpanded: Boolean = false,
    onExpandCallback: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .animateContentSize(SnapSpec()),
    ) {
        Text(
            modifier = if (!isExpanded) Modifier.clickable { onExpandCallback() } else Modifier,
            text = paymentDetailsLabel,
            style = if (isExpanded)
                SDKTheme.typography.s14Normal.copy(
                    color = SDKTheme.colors.brand.copy(alpha = 0.3f)
                )
            else
                SDKTheme.typography.s14Normal.copy(
                    color = SDKTheme.colors.brand
                ),
            //modifier = Modifier.align(Alignment.Start)
        )
        if (isExpanded) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
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