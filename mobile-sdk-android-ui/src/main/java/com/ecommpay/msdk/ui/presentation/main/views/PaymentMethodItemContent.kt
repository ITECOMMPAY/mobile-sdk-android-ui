package com.ecommpay.msdk.ui.presentation.main.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.views.button.PayButton

@Composable
fun PaymentMethodItemContent(
    payLabel: String,
    amount: String,
    currency: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    var isPayButtonEnabled by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        content = {
            content()
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
            PayButton(
                payLabel = payLabel,
                amount = amount,
                currency = currency,
                isEnabled = isPayButtonEnabled
            ) {

            }
        }
    )
}