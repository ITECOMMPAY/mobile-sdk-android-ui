package com.ecommpay.msdk.ui.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import com.google.accompanist.navigation.animation.composable
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV.EnterCVVState
import com.ecommpay.msdk.ui.paymentMethod.PaymentMethodState
import com.ecommpay.msdk.ui.entry.EntryState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationState(
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = "entryScreen",
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }) {
        composable(
            route = "entryScreen"
        ) {
            EntryState(
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
        composable(
            route = "paymentMethodScreen/{title}",
        )
        {
            PaymentMethodState(
                arguments = it.arguments,
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
        composable(
            route = "enterCVV/{id}"
        ) {
            EnterCVVState(
                arguments = it.arguments,
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
    }
}