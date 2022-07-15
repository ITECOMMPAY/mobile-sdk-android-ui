package com.paymentpage.msdk.ui.base.mvvm

import androidx.lifecycle.ViewModel
import com.paymentpage.msdk.ui.base.mvi.Reducer
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : UiState, in E : UiEvent> : ViewModel() {
    abstract val state: StateFlow<S>
    internal abstract val reducer: Reducer<S, E>
    abstract val timeMachine: TimeMachine<S>

    internal fun sendEvent(event: E) {
        reducer.sendEvent(event)
    }
}