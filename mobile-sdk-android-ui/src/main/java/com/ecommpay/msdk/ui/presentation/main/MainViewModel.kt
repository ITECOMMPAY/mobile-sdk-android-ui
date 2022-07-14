package com.ecommpay.msdk.ui.presentation.main

import com.ecommpay.msdk.ui.models.PaymentInfo
import com.ecommpay.msdk.core.domain.interactors.pay.PayInteractor
import com.ecommpay.msdk.ui.base.mvi.Reducer
import com.ecommpay.msdk.ui.base.mvi.TimeMachine
import com.ecommpay.msdk.ui.base.mvvm.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    private val payInteractor: PayInteractor,
    private val paymentInfo: PaymentInfo
) : BaseViewModel<MainScreenState, MainScreenUiEvent>() {
    override val reducer = MainReducer(MainScreenState.initial())

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<MainScreenState>
        get() = reducer.timeMachine


    class MainReducer(initial: MainScreenState) :
        Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {

        }
    }
}