package com.ecommpay.ui.msdk.sample.ui.navigation

import androidx.compose.runtime.Composable
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavRoutes
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewState
import com.ecommpay.ui.msdk.sample.ui.additionalFields.AdditionalFieldsState
import com.ecommpay.ui.msdk.sample.ui.main.MainState
import com.ecommpay.ui.msdk.sample.ui.recurrent.RecurrentState
import com.ecommpay.ui.msdk.sample.ui.threeDSecure.ThreeDSecureState
import kotlin.system.exitProcess

@Composable
fun NavigationScreen(
    viewState: NavigationViewState,
    listener: (NavigationViewIntents) -> Unit
) {
    when (val currentRoute = viewState.currentRoute) {
        is MainHostScreens.MainScreen -> MainState(route = currentRoute)
        is MainHostScreens.AdditionalFields -> AdditionalFieldsState(route = currentRoute)
        is MainHostScreens.Recurrent -> RecurrentState(route = currentRoute)
        is MainHostScreens.ThreeDSecure -> ThreeDSecureState(route = currentRoute)
        is NavRoutes.Exit -> exitProcess(0)
    }
}