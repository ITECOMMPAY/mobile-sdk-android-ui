package com.paymentpage.ui.msdk.sample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldScreen
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainState
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewActions
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.RecurrentState
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.ThreeDSecureState


@Composable
fun NavigationComponent(
    mainViewActionListener: (MainViewActions) -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main.route,
    ) {
        composable(NavRoutes.Main.route) {
            MainState(navController = navController, mainViewActionListener = mainViewActionListener)
        }
        composable(NavRoutes.AdditionalFields.route) {
            AdditionalFieldScreen(navController = navController)
        }
        composable(NavRoutes.Recurrent.route) {
            RecurrentState(navController = navController, mainViewActionListener = mainViewActionListener)
        }
        composable(NavRoutes.ThreeDSecure.route) {
            ThreeDSecureState(navController = navController, mainViewActionListener = mainViewActionListener)
        }
    }
}