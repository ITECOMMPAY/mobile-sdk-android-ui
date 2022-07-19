@file:OptIn(ExperimentalAnimationApi::class)

package com.paymentpage.msdk.ui.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.presentation.clarificationFields.ClarificationFieldsScreen
import com.paymentpage.msdk.ui.presentation.customerFields.CustomerFieldsScreen
import com.paymentpage.msdk.ui.presentation.init.InitScreen
import com.paymentpage.msdk.ui.presentation.loading.LoadingScreen
import com.paymentpage.msdk.ui.presentation.main.MainScreen
import com.paymentpage.msdk.ui.presentation.result.ResultScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
internal fun NavigationComponent(navigator: Navigator, delegate: PaymentDelegate) {
    val navController = rememberAnimatedNavController()

    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { navController.navigate(it.getPath()) }.launchIn(this)
    }

    val viewModel = LocalMainViewModel.current
    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when {
                it.isLoading == true -> navigator.navigateTo(Route.Loading)
                it.customerFields.isNotEmpty() -> navigator.navigateTo(Route.CustomerFields)
                it.clarificationFields.isNotEmpty() -> navigator.navigateTo(Route.ClarificationFields)
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
            BackHandler(true) { }
            InitScreen(navigator = navigator, delegate = delegate)
        }
        composable(route = Route.Main.getPath()) {
            MainScreen(
                navigator = navigator,
                delegate = delegate
            )
        }
        composable(route = Route.CustomerFields.getPath()) {
            CustomerFieldsScreen()
        }
        composable(route = Route.ClarificationFields.getPath()) {
            BackHandler(true) { }
            ClarificationFieldsScreen()
        }
        composable(route = Route.Result.getPath()) {
            BackHandler(true) { }
            ResultScreen()
        }
        composable(route = Route.Loading.getPath()) {
            BackHandler(true) { }
            LoadingScreen()
        }
    }
}