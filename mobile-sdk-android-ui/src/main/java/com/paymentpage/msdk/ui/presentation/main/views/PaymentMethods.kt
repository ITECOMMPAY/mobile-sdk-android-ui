@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.presentation.main.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.views.method.PaymentMethodItem
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods


@Composable
internal fun PaymentMethodList(
    paymentMethods: List<PaymentMethod>,
    savedAccounts: List<SavedAccount>,
    additionalFields: List<com.paymentpage.msdk.ui.AdditionalField>,
    onItemSelected: ((method: UiPaymentMethod) -> Unit) //callback for show vat info
) {

    val mergedPaymentMethods = paymentMethods.mergeUIPaymentMethods(savedAccounts = savedAccounts)
    if (mergedPaymentMethods.isEmpty()) return

    var selectedPaymentMethod by remember {
        mutableStateOf<UiPaymentMethod?>(
            if (mergedPaymentMethods.first() is UiPaymentMethod.UIGooglePayPaymentMethod) //if first method is google pay
                mergedPaymentMethods[1.coerceAtMost(mergedPaymentMethods.size - 1)]
            else
                mergedPaymentMethods.first()
        )
    }

    LaunchedEffect(Unit) {
        if (selectedPaymentMethod != null)
            onItemSelected(selectedPaymentMethod!!)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        mergedPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(
                isExpand = selectedPaymentMethod?.index == uiPaymentMethod.index,
                method = uiPaymentMethod,
                onItemSelected = {
                    selectedPaymentMethod = it
                    onItemSelected(it)
                },
                onItemUnSelected = {
                    selectedPaymentMethod = null
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        }
    }
}
