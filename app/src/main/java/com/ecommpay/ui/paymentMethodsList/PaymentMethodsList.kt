package com.ecommpay.ui.paymentMethodsList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.ui.base.DefaultViewActions
import com.ecommpay.ui.base.ViewActions
import com.ecommpay.ui.base.ViewStates
import com.ecommpay.ui.navigation.NavigationViewActions

@Composable
fun PaymentMethodsList(
    isInit: Boolean = false,
    paymentMethodsListViewModel: PaymentMethodsListViewModel = viewModel(),
    navigationActionListener: (navigationAction: NavigationViewActions) -> Unit,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<PaymentMethodsListViewData>? by paymentMethodsListViewModel.viewState.observeAsState()
    val viewAction: ViewActions? by paymentMethodsListViewModel.viewAction.observeAsState()
    LaunchedEffect(key1 = paymentMethodsListViewModel) {
        if (isInit) {
            paymentMethodsListViewModel.entryPoint()
        }
    }
    viewAction?.Invoke {
        when (viewAction) {
            is NavigationViewActions -> navigationActionListener(viewAction as NavigationViewActions)
            is DefaultViewActions -> defaultActionListener(viewAction as DefaultViewActions)
        }
    }
    state?.let {
        PaymentMethodsListScreen(
            state = it,
            intentListener = { intent -> paymentMethodsListViewModel.pushIntent(intent) }
        )
    }
}