package com.ecommpay.msdk.ui.bottomSheetScreens.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewActions
import com.ecommpay.msdk.ui.base.ViewStates

@Composable
fun ResultState(
    resultViewModel: ResultViewModel = viewModel(),
    navController: NavHostController,
    defaultActionListener: (defaultAction: DefaultViewActions) -> Unit,
) {
    val state: ViewStates<ResultViewData> by resultViewModel.viewState.observeAsState(DefaultViewStates.Default(resultViewModel.defaultViewData()))
    val viewAction: ViewActions? by resultViewModel.viewAction.observeAsState()

    when (state) {
        is DefaultViewStates.Default -> resultViewModel.entryPoint()
    }
    viewAction?.Invoke {
        when (viewAction) {
            is DefaultViewActions -> defaultActionListener(viewAction as DefaultViewActions)
        }
    }
    ResultScreen(
        state = state,
        intentListener = { intent -> resultViewModel.pushIntent(intent) })
}