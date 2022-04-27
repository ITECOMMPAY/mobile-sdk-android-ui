package com.ecommpay.ui.paymentMethod

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.ui.base.*
import com.ecommpay.ui.navigation.NavigationViewActions

@Composable
fun PaymentMethod(
    title: String,
    paymentMethodViewModel: PaymentMethodViewModel = viewModel(),
    navigationActionListener: (navigationAction: NavigationViewActions) -> Unit,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<PaymentMethodViewData>? by paymentMethodViewModel.viewState.observeAsState()
    val viewAction: ViewActions? by paymentMethodViewModel.viewAction.observeAsState()

    LaunchedEffect(key1 = paymentMethodViewModel) {
        paymentMethodViewModel.paymentMethodEntryPoint(
            title = title)
    }
    viewAction?.Invoke {
        when (viewAction) {
            is NavigationViewActions -> navigationActionListener(viewAction as NavigationViewActions)
            is DefaultViewActions -> defaultActionListener(viewAction as DefaultViewActions)
        }
    }
    state?.let {
        PaymentMethodScreen(
            state = it,
            intentListener = { intent -> paymentMethodViewModel.pushIntent(intent) })
    }
}