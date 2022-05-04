package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewActions
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.navigation.NavigationViewActions

@Composable
fun EnterCVVState(
    arguments: Bundle?,
    enterCVVViewModel: EnterCVVViewModel = viewModel(),
    navController: NavHostController,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<EnterCVVViewData>? by enterCVVViewModel.viewState.observeAsState()
    val viewAction: ViewActions? by enterCVVViewModel.viewAction.observeAsState()

    when (state) {
        is DefaultViewStates.Default -> enterCVVViewModel.entryPoint(arguments)
    }
    viewAction?.Invoke {
        when (viewAction) {

        }
    }
    EnterCVVScreen(
        state = state,
        intentListener = { intent -> enterCVVViewModel.pushIntent(intent) })
}