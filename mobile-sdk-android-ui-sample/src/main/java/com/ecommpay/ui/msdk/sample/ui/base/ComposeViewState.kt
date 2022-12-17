package com.ecommpay.ui.msdk.sample.ui.base

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewActions
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewState
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.ecommpay.ui.msdk.sample.utils.CollectAsEffect

@Composable
fun <VI : ViewIntents, VS : ViewState> ComposeViewState(
    viewUseCase: BaseViewUC<VI, VS>,
    actionListener: (ViewActions) -> Unit = {},
    content: @Composable (VS, (VI) -> Unit) -> Unit
) {
    BackHandler {
        viewUseCase.pushExternalIntent(NavigationViewIntents.Back())
    }
    viewUseCase.viewAction.CollectAsEffect { viewAction ->
        actionListener(viewAction)
    }
    val viewState by viewUseCase.viewState.collectAsState()
    content(viewState) { intent -> viewUseCase.pushIntent(intent) }
}