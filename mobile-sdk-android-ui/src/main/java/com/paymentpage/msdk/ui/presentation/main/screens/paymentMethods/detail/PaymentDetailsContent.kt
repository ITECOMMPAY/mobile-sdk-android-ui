package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
fun PaymentDetailsContent(
    actionType: SDKActionType,
    paymentIdLabel: String,
    paymentIdValue: String,
    paymentDescriptionLabel: String,
    paymentDescriptionValue: String?,
    merchantAddressLabel: String,
    merchantAddressValue: String?
) {
    Column {
        //Payment ID
        if (actionType != SDKActionType.Verify) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = paymentIdLabel,
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = paymentIdValue,
                style = SDKTheme.typography.s14Bold.copy(color = Color.White)
            )
        }
        //Description
        if (paymentDescriptionValue != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = paymentDescriptionLabel,
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = paymentDescriptionValue,
                style = SDKTheme.typography.s14Light.copy(color = Color.White)
            )
        }
        //Address
        if (merchantAddressValue != null) {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = merchantAddressLabel,
                style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = merchantAddressValue,
                style = SDKTheme.typography.s14Light.copy(color = Color.White)
            )
        }
    }
}