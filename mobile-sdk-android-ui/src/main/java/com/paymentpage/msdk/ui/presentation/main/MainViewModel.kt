package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePage
import com.paymentpage.msdk.core.domain.interactors.card.remove.CardRemoveDelegate
import com.paymentpage.msdk.core.domain.interactors.pay.PayDelegate
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import com.paymentpage.msdk.ui.core.CardRemoveInteractorProxy
import com.paymentpage.msdk.ui.core.PayInteractorProxy
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    val payInteractor: PayInteractorProxy,
    val cardRemoveInteractor: CardRemoveInteractorProxy,
) : BaseViewModel<MainScreenState, MainScreenUiEvent>(), PayDelegate, CardRemoveDelegate {

    init {
        payInteractor.delegate = this
        cardRemoveInteractor.addDelegate(this)
    }

    override val reducer = MainReducer(MainScreenState())

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<MainScreenState>
        get() = reducer.timeMachine

    val payment: Payment?
        get() = _payment
    private var _payment: Payment? = null

    override fun onCleared() {
        super.onCleared()
        payInteractor.cancel()
        cardRemoveInteractor.removeDelegate(this)
        cardRemoveInteractor.cancel()
    }


    override fun onClarificationFields(
        clarificationFields: List<ClarificationField>,
        payment: Payment
    ) {
        sendEvent(MainScreenUiEvent.ShowClarificationFields(clarificationFields = clarificationFields))
    }

    override fun onCompleteWithDecline(
        isTryAgain: Boolean,
        paymentMessage: String?,
        payment: Payment
    ) {
        //sendEvent(MainScreenUiEvent.SetPayment(payment))
        this._payment = payment
        sendEvent(
            MainScreenUiEvent.ShowDeclinePage(
                paymentMessage = paymentMessage,
                isTryAgain = isTryAgain
            )
        )
    }

    override fun onCompleteWithSuccess(payment: Payment) {
        //sendEvent(MainScreenUiEvent.SetPayment(payment))
        this._payment = payment
        sendEvent(MainScreenUiEvent.ShowSuccessPage)
    }

    override fun onCustomerFields(customerFields: List<CustomerField>) {
        val visibleFields = customerFields.filter { !it.isHidden }
        if (visibleFields.isNotEmpty())
            sendEvent(MainScreenUiEvent.ShowCustomerFields(customerFields = visibleFields))
    }

    override fun onError(code: ErrorCode, message: String) {
        sendEvent(MainScreenUiEvent.ShowError(ErrorResult(code = code, message = message)))
    }

    override fun onStartingRemove() {
        sendEvent(MainScreenUiEvent.ShowDeleteCardLoading(isLoading = true))
    }

    override fun onSuccess(result: Boolean) {
        sendEvent(MainScreenUiEvent.ShowDeleteCardLoading(isLoading = false))
    }

    override fun onPaymentCreated() {
    }

    override fun onStatusChanged(status: PaymentStatus, payment: Payment) {
        this._payment = payment
    }

    override fun onThreeDSecure(
        threeDSecurePage: ThreeDSecurePage,
        isCascading: Boolean,
        payment: Payment
    ) {
        sendEvent(
            MainScreenUiEvent.ShowThreeDSecurePage(
                threeDSecurePage = threeDSecurePage,
                isCascading = isCascading
            )
        )
    }

    override fun onThreeDSecureRedirectComplete() {
        sendEvent(MainScreenUiEvent.ShowLoading)
    }
}
