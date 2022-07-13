@file:Suppress("UNUSED_PARAMETER")

package com.ecommpay.msdk.ui.presentation.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.method.PaymentMethodItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.core.mergeUIPaymentMethods

@Composable
internal fun PaymentMethodList(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null //callback for show vat info
) {

    val mergedPaymentMethods =
        paymentMethods.mergeUIPaymentMethods(savedAccounts)

    if (mergedPaymentMethods.isEmpty()) return

    var selectedPaymentMethod by remember { mutableStateOf(mergedPaymentMethods.first()) }


    LaunchedEffect(Unit) {
        val firstPaymentMethodCustomerFields =
            mergedPaymentMethods.first().paymentMethod?.customerFields
        if (mergedPaymentMethods.first() is UIPaymentMethod.UIGooglePayPaymentMethod && firstPaymentMethodCustomerFields.isNullOrEmpty()) //if first method is google pay without customer fields
            selectedPaymentMethod =
                mergedPaymentMethods[1.coerceAtMost(mergedPaymentMethods.size - 1)]
        onItemSelected?.invoke(selectedPaymentMethod)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        mergedPaymentMethods.forEach {
            PaymentMethodItem(
                isExpand = selectedPaymentMethod.index == it.index,
                paymentOptions = paymentOptions,
                method = it
            ) { method ->
                selectedPaymentMethod = method
                onItemSelected?.invoke(method)
            }
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        }
    }
}
