package com.ecommpay.msdk.ui.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentDelegate
import com.ecommpay.msdk.ui.model.init.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.init.InitScreen
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.ecommpay.msdk.ui.presentation.result.ResultScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavigationComponent(navigator: Navigator, delegate: PaymentDelegate) {
    val navController = rememberAnimatedNavController()

    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { navController.navigate(it.getPath()) }.launchIn(this)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Init.getPath(),
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = {
            EnterTransition.None
        },
        popExitTransition = {
            ExitTransition.None
        }
    ) {
        composable(route = Route.Init.getPath()) {
            BackHandler(true) { }
            InitScreen(navigator = navigator, delegate = delegate)
        }
        composable(
            route = Route.Main.getPath()
        ) {
            BackHandler(true) { }
            MainScreen(
                navigator = navigator,
                paymentMethods = PaymentActivity.msdkSession.getPaymentMethods()?.let { list ->
                    list.map {
                        UIPaymentMethod(
                            code = it.code,
                            name = it.name ?: "",
                            iconUrl = it.iconUrl
                        )
                    }
                } ?: emptyList()
            )
        }
        composable(route = "${Route.Result}") {
            BackHandler(true) { }
            ResultScreen()
        }
    }
}