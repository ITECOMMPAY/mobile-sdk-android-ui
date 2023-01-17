@file:OptIn(ExperimentalAnimationApi::class)

package com.paymentpage.msdk.ui.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.init.InitScreen
import com.paymentpage.msdk.ui.presentation.main.MainScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun RootNavigationView(
    actionType: SDKActionType,
    navigator: Navigator,
    delegate: PaymentDelegate,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val navController = rememberAnimatedNavController()
    val focusManager = LocalFocusManager.current
    val mainScreenNavigator = remember { Navigator() }

    LaunchedEffect("rootNavigation") {
        navigator.sharedFlow.onEach {
            focusManager.clearFocus()
            navController.navigate(it.getPath())
        }.launchIn(this)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Init.getPath(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = Route.Init.getPath()) {//RootNavigationView <-InitScreen
            InitScreen(
                actionType = actionType,
                navigator = navigator,
                onCancel = onCancel,
                onError = onError)
        }
        composable(route = Route.Main.getPath()) { //RootNavigationView <-MainScreen <- inner AnimatedNavHost
            MainScreen(
                startRoute = Route.PaymentMethods,
                actionType = actionType,
                mainScreenNavigator = mainScreenNavigator,
                delegate = delegate,
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.Tokenize.getPath()) { //RootNavigationView <-MainScreen <- inner AnimatedNavHost
            MainScreen(
                startRoute = Route.Tokenize,
                actionType = actionType,
                mainScreenNavigator = mainScreenNavigator,
                delegate = delegate,
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.Restore.getPath()) { //RootNavigationView <-MainScreen <- inner AnimatedNavHost
            MainScreen(
                startRoute = Route.Loading,
                actionType = actionType,
                mainScreenNavigator = mainScreenNavigator,
                delegate = delegate,
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.RestoreAps.getPath()) { //RootNavigationView <-MainScreen <- inner AnimatedNavHost
            MainScreen(
                startRoute = Route.ApsPage,
                actionType = actionType,
                mainScreenNavigator = mainScreenNavigator,
                delegate = delegate,
                onCancel = onCancel,
                onError = onError
            )
        }
    }
}