package com.ecommpay.msdk.ui.navigation


import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV.EnterCVVScreen
import com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV.EnterCVVState
import com.ecommpay.msdk.ui.paymentMethod.PaymentMethodState
import com.ecommpay.msdk.ui.entry.EntryState

@Composable
fun NavigationState(
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "entryScreen") {
        composable("entryScreen") {
            EntryState(
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
        composable("paymentMethodScreen/{title}")
        {
            PaymentMethodState(
                arguments = it.arguments,
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
        composable("enterCVV/{id}") {
            EnterCVVState(
                arguments = it.arguments,
                navController = navController,
                defaultActionListener = defaultActionListener)
        }
    }
}