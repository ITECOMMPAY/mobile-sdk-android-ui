package com.ecommpay.msdk.ui.navigation


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.bottomSheetScreens.result.ResultState
import com.ecommpay.msdk.ui.presentation.main.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun NavigationState(
//    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
//) {
//    val navController = rememberAnimatedNavController()
//    AnimatedNavHost(
//        navController = navController,
//        startDestination = "entryScreen",
//        enterTransition = {
//            EnterTransition.None
//        },
//        exitTransition = {
//            ExitTransition.None
//        }) {
//        composable(
//            route = "entryScreen"
//        ) {
//            MainScreen(navController = navController)
//        }
//        composable(
//            route = "resultScreen"
//        ) {
//            ResultState(
//                navController = navController,
//                defaultActionListener = defaultActionListener
//            )
//        }
//    }
//}