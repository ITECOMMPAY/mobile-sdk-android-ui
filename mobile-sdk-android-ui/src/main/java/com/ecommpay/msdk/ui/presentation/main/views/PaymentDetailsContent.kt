package com.ecommpay.msdk.ui.presentation.main.views

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsContent(
    paymentIDLabel: String?,
    paymentIDValue: String?,
    paymentDescriptionLabel: String?,
    paymentDescriptionValue: String?,
    paymentAddressLabel: String?,
    paymentAddressValue: String?,
    closeButtonCallback: () -> Unit
) {
    Column {
        //Payment ID
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = paymentIDLabel ?: "",
                style = SDKTheme.typography.s14Normal.copy(color = Color.DarkGray)
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
                colorFilter = ColorFilter.tint(SDKTheme.colors.expandedPaymentDetailsCloseButton),
                contentDescription = "",
            )
        }
        if (paymentIDValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = paymentIDValue,
                style = SDKTheme.typography.s14Bold
            )
        }
        //Description
        if (paymentDescriptionLabel != null && paymentDescriptionValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp25))
            Text(
                text = paymentDescriptionLabel,
                style = SDKTheme.typography.s14Normal.copy(color = Color.DarkGray)
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = paymentDescriptionValue,
                style = SDKTheme.typography.s14Normal
            )
        }
        //Address
        if (paymentAddressLabel != null && paymentAddressValue != null) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp25))
            Text(
                text = paymentAddressLabel,
                style = SDKTheme.typography.s14Normal.copy(color = Color.DarkGray)
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp5))
            Text(
                text = paymentAddressValue,
                style = SDKTheme.typography.s14Normal
            )
        }
    }
}