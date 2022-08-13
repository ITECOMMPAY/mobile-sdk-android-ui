package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.ui.ActionType
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    actionType: ActionType,
    mainScreenNavigator: Navigator,
    delegate: PaymentDelegate,
    onError: (ErrorResult, Boolean) -> Unit,
    onCancel: () -> Unit
) {
    val navController = rememberAnimatedNavController()
    val focusManager = LocalFocusManager.current

    LaunchedEffect("mainScreenNavigation") {
        mainScreenNavigator.sharedFlow.onEach {
            focusManager.clearFocus()
            navController.navigate(it.getPath())
        }.launchIn(this)
    }

    val mainViewModel = LocalMainViewModel.current
    LaunchedEffect(Unit) {
        mainViewModel.state.onEach {
            when {
                it.error != null -> onError(it.error, true)
                it.isLoading == true && mainScreenNavigator.lastRoute != Route.Loading ->
                    mainScreenNavigator.navigateTo(Route.Loading)
                it.finalPaymentState != null -> {
                    when (it.finalPaymentState) {
                        is FinalPaymentState.Success -> mainScreenNavigator.navigateTo(Route.SuccessResult)
                        is FinalPaymentState.Decline -> mainScreenNavigator.navigateTo(Route.DeclineResult)
                    }
                }
                it.customerFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.CustomerFields)
                it.clarificationFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.ClarificationFields)
                it.acsPageState != null -> mainScreenNavigator.navigateTo(Route.AcsPage)
                it.apsPageState != null -> mainScreenNavigator.navigateTo(Route.ApsPage)
            }
        }.collect()
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.PaymentMethods.getPath(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = Route.CustomerFields.getPath()) {
            CustomerFieldsScreen(
                onBack = {
                    navController.navigateUp()
                    navController.navigateUp()
                },
                onCancel = onCancel
            )
        }
        composable(route = Route.ClarificationFields.getPath()) {
            ClarificationFieldsScreen(onCancel = onCancel)
        }
        composable(route = Route.AcsPage.getPath()) {
            ThreeDSecureScreen(onCancel = onCancel)
        }
        composable(route = Route.ApsPage.getPath()) {
            ApsScreen(onCancel = onCancel)
        }
        composable(route = Route.SuccessResult.getPath()) {
            ResultSuccessScreen(onClose = { delegate.onCompleteWithSuccess(it) })
        }

        composable(route = Route.DeclineResult.getPath()) {
            ResultDeclineScreen(onClose = { delegate.onCompleteWithDecline(it) })
        }
        composable(route = Route.Loading.getPath()) {
            LoadingScreen(onCancel = onCancel)
        }
        composable(route = Route.PaymentMethods.getPath()) {
            PaymentMethodsScreen(onCancel = onCancel, onError = onError)
        }
    }

}