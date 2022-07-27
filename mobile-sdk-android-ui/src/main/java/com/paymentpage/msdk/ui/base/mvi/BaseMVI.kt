package com.paymentpage.msdk.ui.base.mvi


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, in E : UiEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    val timeMachine: TimeMachine<S> = TimeTravelMachine { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        timeMachine.addState(initialVal)
    }

    fun sendEvent(event: E) = reduce(_state.value, event)

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)

        if (com.paymentpage.msdk.ui.BuildConfig.IS_TIME_TRAVEL && success) {
            timeMachine.addState(newState)
        }
    }

    abstract fun reduce(oldState: S, event: E)
}

//Screen state
interface UiState

//event from user interaction like button click or item select
interface UiEvent