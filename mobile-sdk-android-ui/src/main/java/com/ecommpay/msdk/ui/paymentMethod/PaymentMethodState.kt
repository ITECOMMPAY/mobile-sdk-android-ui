package com.ecommpay.msdk.ui.paymentMethod

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

@Composable
fun PaymentMethodState(
    arguments: Bundle?,
    paymentMethodViewModel: PaymentMethodViewModel = viewModel(),
    navController: NavHostController,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<PaymentMethodViewData>? by paymentMethodViewModel.viewState.observeAsState()
    val viewAction: ViewActions? by paymentMethodViewModel.viewAction.observeAsState()

    when (state) {
        is DefaultViewStates.Default -> paymentMethodViewModel.entryPoint(arguments)
    }
    viewAction?.Invoke {
        when (viewAction) {
            is NavigationViewActions -> navController.popBackStack()
            is DefaultViewActions -> defaultActionListener(viewAction as DefaultViewActions)
        }
    }
    PaymentMethodScreen(
        state = state,
        intentListener = { intent -> paymentMethodViewModel.pushIntent(intent) })
}