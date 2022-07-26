@file:OptIn(ExperimentalAnimationApi::class)

package com.paymentpage.msdk.ui.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.presentation.clarificationFields.ClarificationFieldsScreen
import com.paymentpage.msdk.ui.presentation.customerFields.CustomerFieldsScreen
import com.paymentpage.msdk.ui.presentation.init.InitScreen
import com.paymentpage.msdk.ui.presentation.loading.LoadingScreen
import com.paymentpage.msdk.ui.presentation.main.FinalPaymentState
import com.paymentpage.msdk.ui.presentation.main.MainScreen
import com.paymentpage.msdk.ui.presentation.result.ResultDeclineScreen
import com.paymentpage.msdk.ui.presentation.result.ResultSuccessScreen

import com.paymentpage.msdk.ui.presentation.threeDSecure.ThreeDSecureScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavigationComponent(
    navigator: Navigator,
    delegate: PaymentDelegate,
    onCancel: () -> Unit
) {
    val navController = rememberAnimatedNavController()
    val focusManager = LocalFocusManager.current

    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            focusManager.clearFocus()
            navController.navigateUp()
            navController.navigate(it.getPath())
        }.launchIn(this)
    }

    val viewModel = LocalMainViewModel.current
    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when {
                it.isLoading == true -> navigator.navigateTo(Route.Loading)
                it.customerFields.isNotEmpty() -> navigator.navigateTo(Route.CustomerFields)
                it.clarificationFields.isNotEmpty() -> navigator.navigateTo(Route.ClarificationFields)
                it.acsPageState != null -> navigator.navigateTo(Route.AcsPage)
                it.finalPaymentState != null -> {
                    when (it.finalPaymentState) {
                        is FinalPaymentState.Success -> navigator.navigateTo(Route.SuccessResult)
                        is FinalPaymentState.Decline -> navigator.navigateTo(Route.DeclineResult)
                    }
                }
            }
        }.collect()
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Init.getPath(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = Route.Init.getPath()) {
            BackHandler(true) { onCancel() }
            InitScreen(navigator = navigator, delegate = delegate)
        }
        composable(route = Route.Main.getPath()) {
            MainScreen(
                navigator = navigator,
                delegate = delegate,
                onCancel = onCancel
            )
        }
        composable(route = Route.CustomerFields.getPath()) {
            CustomerFieldsScreen(
                onBack = {
                    navController.navigateUp()
                },
                onCancel = onCancel
            )
        }
        composable(route = Route.ClarificationFields.getPath()) {
            ClarificationFieldsScreen(
                onCancel = onCancel
            )
        }
        composable(route = Route.AcsPage.getPath()) {
            ThreeDSecureScreen(
                onCancel = onCancel
            )
        }
        composable(route = Route.SuccessResult.getPath()) {
            ResultSuccessScreen(onClose = { delegate.onCompleteWithSuccess(it) })
        }

        composable(route = Route.DeclineResult.getPath()) {
            ResultDeclineScreen(onClose = { delegate.onCompleteWithDecline(it) })
        }

        composable(route = Route.Loading.getPath()) {
            BackHandler(true) { }
            LoadingScreen()
        }
    }
}