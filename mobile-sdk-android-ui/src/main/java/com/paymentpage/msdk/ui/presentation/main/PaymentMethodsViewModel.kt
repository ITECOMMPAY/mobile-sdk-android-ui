package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.paymentpage.msdk.ui.base.mvi.Reducer
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import com.paymentpage.msdk.ui.core.CardRemoveInteractorProxy
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import kotlinx.coroutines.flow.StateFlow

internal class PaymentMethodsViewModel(val cardRemoveInteractor: CardRemoveInteractorProxy) :
    BaseViewModel<PaymentMethodsState, PaymentMethodsUiEvent>(), CardRemoveDelegate {

    init {
        cardRemoveInteractor.addDelegate(this)
    }

    override fun onCleared() {
        super.onCleared()
        cardRemoveInteractor.removeDelegate(this)
    }

    override val reducer = PaymentMethodsReducer(PaymentMethodsState.initial())

    override val state: StateFlow<PaymentMethodsState>
        get() = reducer.state

    override val timeMachine: TimeMachine<PaymentMethodsState>
        get() = reducer.timeMachine

    fun setCurrentMethod(method: UIPaymentMethod?) {
        sendEvent(PaymentMethodsUiEvent.SetCurrentMethod(method))
    }

    override fun onError(code: ErrorCode, message: String) {

    }

    override fun onSuccess(result: Boolean) {

    }
}


internal class PaymentMethodsReducer(initial: PaymentMethodsState) :
    Reducer<PaymentMethodsState, PaymentMethodsUiEvent>(initial) {
    override fun reduce(oldState: PaymentMethodsState, event: PaymentMethodsUiEvent) {
        when (event) {
            is PaymentMethodsUiEvent.SetCurrentMethod -> {
                setState(oldState.copy(currentMethod = event.method))
            }
        }
    }
}