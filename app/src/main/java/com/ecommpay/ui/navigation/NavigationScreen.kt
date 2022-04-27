package com.ecommpay.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.ui.base.DefaultViewActions
import com.ecommpay.ui.base.ViewActions
import com.ecommpay.ui.paymentMethod.PaymentMethod
import com.ecommpay.ui.paymentMethodsList.PaymentMethodsList

@Composable
fun NavigationScreen(
    isFirstRun: Boolean = true,
    navigationViewModel: NavigationViewModel = viewModel(),
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val navigationAction: ViewActions? by navigationViewModel.navigationState.observeAsState()

    val navigationActionListener: (navigationViewActions: NavigationViewActions) -> Unit = {
        navigationViewModel.setCurrentNavigationState(it)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {

        when (navigationAction) {
            //Entry screen
            is NavigationViewActions.PaymentMethodsListScreen ->
                PaymentMethodsList(
                    isInit = isFirstRun,
                    navigationActionListener = navigationActionListener,
                    defaultActionListener = defaultActionListener)

            is NavigationViewActions.PaymentMethodsListScreenToPaymentMethodScreen ->
                PaymentMethod(
                    title = (navigationAction as NavigationViewActions.PaymentMethodsListScreenToPaymentMethodScreen).name,
                    navigationActionListener = navigationActionListener,
                    defaultActionListener = defaultActionListener)

            is NavigationViewActions.PaymentMethodScreenToPaymentMethodsListScreen ->
                PaymentMethodsList(
                    navigationActionListener = navigationActionListener,
                    defaultActionListener = defaultActionListener)
        }
    }
}