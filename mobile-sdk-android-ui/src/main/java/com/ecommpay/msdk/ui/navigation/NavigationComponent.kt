package com.ecommpay.msdk.ui.navigation


import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ecommpay.msdk.ui.PaymentDelegate
import com.ecommpay.msdk.ui.model.init.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.init.InitScreen
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.ecommpay.msdk.ui.presentation.result.ResultScreen
import com.ecommpay.msdk.ui.utils.extensions.getData
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavigationComponent(delegate: PaymentDelegate) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Init.getPath(),
//        enterTransition = {
//            slideIntoContainer(
//                AnimatedContentScope.SlideDirection.Up,
//                animationSpec = tween(700)
//            )
//        },
//        exitTransition = {
//            slideOutOfContainer(
//                AnimatedContentScope.SlideDirection.Down,
//                animationSpec = tween(700)
//            )
//        },
//        popEnterTransition = {
//            slideIntoContainer(
//                AnimatedContentScope.SlideDirection.Up,
//                animationSpec = tween(700)
//            )
//        },
//        popExitTransition = {
//            slideOutOfContainer(
//                AnimatedContentScope.SlideDirection.Down,
//                animationSpec = tween(700)
//            )
//        }
    ) {
        composable(route = Route.Init.getPath()) {
            BackHandler(true) { }
            InitScreen(navController = navController, delegate = delegate)
        }
        composable(
            route = Route.Main.getPath(),
            arguments = listOf(navArgument(name = Route.Main.key) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val paymentMethods =
                backStackEntry.getData<List<UIPaymentMethod>>(Route.Main.key) ?: emptyList()
            BackHandler(true) { }
            MainScreen(navController = navController, paymentMethods = paymentMethods)
        }
        composable(route = "${Route.Result}") {
            BackHandler(true) { }
            ResultScreen()
        }
    }
}