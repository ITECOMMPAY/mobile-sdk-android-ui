package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePageType
import com.paymentpage.msdk.ui.*
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
import com.paymentpage.msdk.ui.presentation.main.screens.tokenize.TokenizeScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    startRoute: Route,
    actionType: SDKActionType,
    mainScreenNavigator: Navigator,
    delegate: PaymentDelegate,
    onError: (ErrorResult, Boolean) -> Unit,
    onCancel: () -> Unit
) {
    val lastRoute = mainScreenNavigator.lastRoute

    val navController = rememberAnimatedNavController()
    val focusManager = LocalFocusManager.current


    LaunchedEffect("mainScreenNavigation") {
        mainScreenNavigator.sharedFlow.onEach {
            focusManager.clearFocus()
            navController.navigate(it.getPath())
        }.launchIn(this)
    }

    setupStateListener(mainScreenNavigator = mainScreenNavigator, onError = onError)

    AnimatedNavHost(
        navController = navController,
        startDestination = lastRoute?.getPath() ?: startRoute.getPath(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = Route.CustomerFields.getPath()) {
            CustomerFieldsScreen(
                actionType = actionType,
                onBack = { mainScreenNavigator.navigateTo(Route.PaymentMethods) },
                onCancel = onCancel
            )
        }
        composable(route = Route.ClarificationFields.getPath()) {
            ClarificationFieldsScreen(onCancel = onCancel)
        }

        //3DS
        composable(route = Route.ThreeDSecurePage.getPath()) {
            ThreeDSecureScreen(onCancel = onCancel)
        }
        composable(route = Route.ThreeDSecureLoadingPage.getPath()) {
            ThreeDSecureScreen(onCancel = onCancel)
        }

        composable(route = Route.ApsPage.getPath()) {
            ApsScreen(onCancel = onCancel)
        }
        composable(route = Route.SuccessResult.getPath()) {
            ResultSuccessScreen(
                actionType = actionType,
                onClose = { delegate.onCompleteWithSuccess(it) },
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.DeclineResult.getPath()) {
            ResultDeclineScreen(
                actionType = actionType,
                onClose = { delegate.onCompleteWithDecline(it) },
                onCancel = onCancel,
                onError = onError,
            )
        }
        composable(route = Route.Loading.getPath()) {
            LoadingScreen(onCancel = onCancel)
        }
        composable(route = Route.PaymentMethods.getPath()) {
            PaymentMethodsScreen(
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.Tokenize.getPath()) {
            TokenizeScreen(
                onCancel = onCancel,
                onError = onError
            )
        }
    }
}

@Composable
private fun setupStateListener(
    mainScreenNavigator: Navigator,
    onError: (ErrorResult, Boolean) -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()
    val savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList()
    LaunchedEffect(Unit) {
        mainViewModel.state.onEach {
            when {
                it.error != null -> onError(it.error, true)
                it.isLoading == true -> mainScreenNavigator.navigateTo(Route.Loading)
                it.isTryAgain == true -> {
                    paymentMethodsViewModel.updatePaymentMethods(
                        paymentMethods = paymentMethods,
                        savedAccounts = savedAccounts
                    )
                    mainScreenNavigator.navigateTo(Route.PaymentMethods)
                }
                it.finalPaymentState != null -> {
                    when (it.finalPaymentState) {
                        is FinalPaymentState.Success -> mainScreenNavigator.navigateTo(Route.SuccessResult)
                        is FinalPaymentState.Decline -> mainScreenNavigator.navigateTo(Route.DeclineResult)
                    }
                }
                it.customerFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.CustomerFields)
                it.clarificationFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.ClarificationFields)
                it.threeDSecurePageState != null -> when (it.threeDSecurePageState.threeDSecurePage?.type) {
                    ThreeDSecurePageType.THREE_DS_2_FRICTIONLESS ->
                        mainScreenNavigator.navigateTo(Route.ThreeDSecureLoadingPage)
                    else -> mainScreenNavigator.navigateTo(Route.ThreeDSecurePage)
                }
                it.apsPageState != null -> mainScreenNavigator.navigateTo(Route.ApsPage)
            }
        }.collect()
    }
}
