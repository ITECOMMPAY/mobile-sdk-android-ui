@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalMsdkSession
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.PaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod

@Composable
internal fun PaymentMethodList(uiPaymentMethods: List<UIPaymentMethod>) {
    val mainViewModel = LocalMainViewModel.current
    val state = mainViewModel.state.collectAsState().value //for recomposition
    val lastSelectedMethod = state.currentMethod


    val savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList()

    //filter if saved accounts changed
    val filteredUIPaymentMethods = uiPaymentMethods.filter { paymentMethod ->
        if (paymentMethod is UIPaymentMethod.UISavedCardPayPaymentMethod) {
            savedAccounts.isNotEmpty() && savedAccounts.map { it.id }
                .contains(paymentMethod.savedAccount.id)
        } else true
    }

    if (filteredUIPaymentMethods.isEmpty()) return

    LaunchedEffect(Unit) {
        val lastOpenedMethod = mainViewModel.lastState.currentMethod
        val openedMethod = lastOpenedMethod
            ?: if (filteredUIPaymentMethods.first() is UIPaymentMethod.UIGooglePayPaymentMethod) //if first method is google pay
                filteredUIPaymentMethods[1.coerceAtMost(filteredUIPaymentMethods.size - 1)]
            else //first by default
                filteredUIPaymentMethods.first()
        mainViewModel.setCurrentMethod(openedMethod)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        filteredUIPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(
                method = if (lastSelectedMethod?.index == uiPaymentMethod.index) lastSelectedMethod else uiPaymentMethod
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}
