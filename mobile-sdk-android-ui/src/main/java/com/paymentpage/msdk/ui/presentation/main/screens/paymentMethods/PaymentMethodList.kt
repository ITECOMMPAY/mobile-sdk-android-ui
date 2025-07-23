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
import com.paymentpage.msdk.ui.LocalMsdkSession
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.PaymentMethodItem
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod

@Composable
internal fun PaymentMethodList(
    actionType: SDKActionType,
    uiPaymentMethods: List<UIPaymentMethod>
) {
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current

    val lastSelectedMethod = paymentMethodsViewModel.state.collectAsState().value.currentMethod

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
        val openedMethod = paymentMethodsViewModel.state.value.currentMethod ?:
        filteredUIPaymentMethods
            .filterNot { it is UIPaymentMethod.UIGooglePayPaymentMethod }
            .firstOrNull()

        if (openedMethod != null) {
            paymentMethodsViewModel.setCurrentMethod(openedMethod)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        val isOnlyOneMethodOnScreen = filteredUIPaymentMethods.size == 1
        filteredUIPaymentMethods.forEach { uiPaymentMethod ->
            PaymentMethodItem(
                method = if (lastSelectedMethod?.index == uiPaymentMethod.index) lastSelectedMethod else uiPaymentMethod,
                actionType = actionType,
                isOnlyOneMethodOnScreen = isOnlyOneMethodOnScreen
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}
