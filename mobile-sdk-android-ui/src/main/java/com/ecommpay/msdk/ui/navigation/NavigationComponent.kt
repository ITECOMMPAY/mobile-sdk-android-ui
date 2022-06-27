package com.ecommpay.msdk.ui.navigation


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ecommpay.msdk.ui.presentation.init.InitScreen
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.ecommpay.msdk.ui.presentation.main.data.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.result.ResultScreen
import com.ecommpay.msdk.ui.utils.extensions.getData
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@ExperimentalAnimationApi
@Composable
fun NavigationComponent() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "${Route.Init}",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Up,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(700)
            )
        }
    ) {
        composable(route = "${Route.Init}") {
            InitScreen(navController = navController)
        }
        composable(
            route = "${Route.Main}",
            arguments = listOf(navArgument(name = Route.Main.key) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val paymentMethods =
                backStackEntry.getData<List<UIPaymentMethod>>(Route.Main.key) ?: emptyList()
            MainScreen(navController = navController, paymentMethods = paymentMethods)
        }
        composable(route = "${Route.Result}") {
            ResultScreen()
        }
    }
}