package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod

@Composable
internal fun PaymentMethodItem(
    isExpand: Boolean,
    paymentMethod: UIPaymentMethod,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    ExpandablePaymentMethodItem(
        index = paymentMethod.index,
        name = paymentMethod.title,
        onExpand = {
            onItemSelected?.invoke(paymentMethod)
        },
        isExpanded = isExpand
    ) {
        Spacer(
            modifier = Modifier // testing content
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}
