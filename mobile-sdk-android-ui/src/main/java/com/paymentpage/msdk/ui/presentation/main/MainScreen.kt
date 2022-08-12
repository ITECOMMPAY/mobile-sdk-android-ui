package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.screens.aps.ApsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.clarificationFields.ClarificationFieldsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.customerFields.CustomerFieldsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.loading.LoadingScreen
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.PaymentMethodsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.result.ResultDeclineScreen
import com.paymentpage.msdk.ui.presentation.main.screens.result.ResultSuccessScreen
import com.paymentpage.msdk.ui.presentation.main.screens.threeDSecure.ThreeDSecureScreen


@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    navigator: Navigator,
    delegate: PaymentDelegate,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val mainViewModel = LocalMainViewModel.current
    val state = mainViewModel.state.collectAsState().value

    when {
        state.error != null -> onError(state.error, true)
        state.isLoading == true && navigator.lastRoute != Route.Loading ->
            LoadingScreen(onCancel = onCancel)
        state.finalPaymentState != null -> {
            when (state.finalPaymentState) {
                is FinalPaymentState.Success ->
                    ResultSuccessScreen(onClose = { delegate.onCompleteWithSuccess(it) })
                is FinalPaymentState.Decline ->
                    ResultDeclineScreen(onClose = { delegate.onCompleteWithDecline(it) })
            }
        }
        state.customerFields.isNotEmpty() ->
            CustomerFieldsScreen(
                onBack = { navigator.navigateUp() },
                onCancel = onCancel
            )
        state.clarificationFields.isNotEmpty() -> ClarificationFieldsScreen(onCancel = onCancel)
        state.apsPageState != null -> ApsScreen(onCancel = onCancel)
        state.acsPageState != null -> ThreeDSecureScreen(onCancel = onCancel)
        else -> PaymentMethodsScreen(onCancel = onCancel, onError = onError)
    }
}