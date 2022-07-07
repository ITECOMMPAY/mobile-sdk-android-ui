package com.ecommpay.msdk.ui.presentation.main.views.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsContent(
    paymentIdLabel: String,
    paymentIdValue: String?,
    paymentDescriptionLabel: String,
    paymentDescriptionValue: String?,
    merchantAddressLabel: String,
    merchantAddressValue: String?,
    closeButtonCallback: () -> Unit
) {
    Column {
        //Payment ID
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = paymentIdLabel,
                style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.secondaryTextColor)
            )
            //Close button
            Image(
                modifier = Modifier.clickable(
                    indication = null, //отключаем анимацию при клике
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        closeButtonCallback()
                    }
                ),
                imageVector = Icons.Default.Close,
                colorFilter = ColorFilter.tint(SDKTheme.colors.iconColor),
                contentDescription = "",
            )
        }
        if (paymentIdValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = paymentIdValue,
                style = SDKTheme.typography.s14Bold
            )
        }
        //Description
        if (paymentDescriptionValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp25))
            Text(
                text = paymentDescriptionLabel,
                style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.secondaryTextColor)
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = paymentDescriptionValue,
                style = SDKTheme.typography.s14Normal
            )
        }
        //Address
        if (merchantAddressValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp25))
            Text(
                text = merchantAddressLabel,
                style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.secondaryTextColor)
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = merchantAddressValue,
                style = SDKTheme.typography.s14Normal
            )
        }
    }
}