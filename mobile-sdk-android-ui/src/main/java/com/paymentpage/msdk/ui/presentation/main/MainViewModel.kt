package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.core.domain.interactors.pay.PayDelegate
import com.paymentpage.msdk.core.domain.interactors.pay.PayInteractor
import com.paymentpage.msdk.ui.PaymentOptions
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    val payInteractor: PayInteractor,
    private val paymentOptions: PaymentOptions
) : BaseViewModel<MainScreenState, MainScreenUiEvent>(), PayDelegate {
    override val reducer = MainReducer(MainScreenState.initial())

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<MainScreenState>
        get() = reducer.timeMachine


    override fun onCleared() {
        super.onCleared()
        payInteractor.cancel()
    }


    override fun onClarificationFields(
        clarificationFields: List<ClarificationField>,
        payment: Payment
    ) {
        sendEvent(MainScreenUiEvent.ShowClarificationFields(clarificationFields = clarificationFields))
    }

    override fun onCompleteWithDecline(paymentMessage: String?, payment: Payment) {
        sendEvent(MainScreenUiEvent.SetPayment(payment))
        sendEvent(
            MainScreenUiEvent.ShowDeclinePage(
                paymentMessage = paymentMessage,
                isTryAgain = false
            )
        )
    }

    override fun onCompleteWithFail(
        isTryAgain: Boolean,
        paymentMessage: String?,
        payment: Payment
    ) {
        sendEvent(MainScreenUiEvent.SetPayment(payment))
        sendEvent(
            MainScreenUiEvent.ShowDeclinePage(
                paymentMessage = paymentMessage,
                isTryAgain = isTryAgain
            )
        )
    }


    override fun onCompleteWithSuccess(payment: Payment) {
        sendEvent(MainScreenUiEvent.SetPayment(payment))
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

    override fun onPaymentCreated() {
        sendEvent(MainScreenUiEvent.ShowLoading)
    }

    override fun onStatusChanged(status: PaymentStatus, payment: Payment) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        sendEvent(MainScreenUiEvent.SetPayment(payment))
    }

    override fun onThreeDSecure(acsPage: AcsPage, isCascading: Boolean, payment: Payment) {
        sendEvent(MainScreenUiEvent.ShowAcsPage(acsPage = acsPage, isCascading = isCascading))
    }
}
