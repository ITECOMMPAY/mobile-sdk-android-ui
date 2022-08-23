@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalMsdkSession
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.PaymentMethodItem
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods

@Composable
internal fun PaymentMethodList() {
    val mainViewModel = LocalMainViewModel.current
    val state = mainViewModel.state.collectAsState().value //for recomposition
    val lastSelectedMethod = state.currentMethod
    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()
    val savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList()
    val mergedPaymentMethods = paymentMethods.mergeUIPaymentMethods(savedAccounts = savedAccounts)
    if (mergedPaymentMethods.isEmpty()) return

    LaunchedEffect(Unit) {
        val lastOpenedMethod = mainViewModel.lastState.currentMethod
        val openedMethod = lastOpenedMethod
            ?: if (mergedPaymentMethods.first() is UIPaymentMethod.UIGooglePayPaymentMethod) //if first method is google pay
                mergedPaymentMethods[1.coerceAtMost(mergedPaymentMethods.size - 1)]
            else //first by default
                mergedPaymentMethods.first()
        mainViewModel.setCurrentMethod(openedMethod)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        mergedPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(
                method = if (lastSelectedMethod?.index == uiPaymentMethod.index) lastSelectedMethod else uiPaymentMethod
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}
