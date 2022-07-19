package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.core.domain.interactors.pay.PayDelegate
import com.paymentpage.msdk.core.domain.interactors.pay.PayInteractor
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.SavedCardSaleRequest
import com.paymentpage.msdk.ui.base.mvi.Reducer
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import com.paymentpage.msdk.ui.presentation.main.models.UiPaymentMethod
import kotlinx.coroutines.flow.StateFlow

internal class MainViewModel(
    private val payInteractor: PayInteractor,
    @Suppress("unused") private val paymentInfo: PaymentInfo
) : BaseViewModel<MainScreenState, MainScreenUiEvent>(), PayDelegate {
    override val reducer = MainReducer(MainScreenState.initial())

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<MainScreenState>
        get() = reducer.timeMachine

    fun saleSavedCard(
        method: UiPaymentMethod,
        accountId: Long,
        cvv: String,
        customerFields: List<CustomerFieldValue> = emptyList()
    ) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        sendEvent(MainScreenUiEvent.SetPaymentMethod(method))
        val request = SavedCardSaleRequest(cvv = cvv, accountId = accountId)
        request.customerFields = customerFields
        payInteractor.execute(request, this)
    }

    fun sendCustomerFields(customerFields: List<CustomerFieldValue>) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        payInteractor.sendCustomerFields(customerFields)
    }

    override fun onCleared() {
        super.onCleared()
        payInteractor.cancel()
    }

    class MainReducer(initial: MainScreenState) :
        Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.ShowLoading -> setState(
                    oldState.copy(
                        customerFields = emptyList(),
                        clarificationFields = emptyList(),
                        isLoading = true,
                    )
                )
                is MainScreenUiEvent.ShowError -> setState(
                    oldState.copy(
                        customerFields = emptyList(),
                        isLoading = false,
                        error = event.error,
                    )
                )
                is MainScreenUiEvent.ShowCustomerFields -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = event.customerFields,
                        clarificationFields = emptyList()
                    )
                )
                is MainScreenUiEvent.ShowClarificationFields -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = emptyList(),
                        clarificationFields = event.clarificationFields
                    )
                )
                is MainScreenUiEvent.SetPaymentMethod -> setState(
                    oldState.copy(method = event.method)
                )
            }
        }
    }

    override fun onClarificationFields(
        clarificationFields: List<ClarificationField>,
        payment: Payment
    ) {
        sendEvent(MainScreenUiEvent.ShowClarificationFields(clarificationFields = clarificationFields))
    }

    override fun onCompleteWithDecline(payment: Payment) {

    }

    override fun onCompleteWithFail(status: String?, payment: Payment) {

    }

    override fun onCompleteWithSuccess(payment: Payment) {

    }

    override fun onCustomerFields(customerFields: List<CustomerField>) {
        sendEvent(MainScreenUiEvent.ShowCustomerFields(customerFields = customerFields))
    }

    override fun onError(code: ErrorCode, message: String) {

    }

    override fun onPaymentCreated() {

    }

    override fun onStatusChanged(status: PaymentStatus, payment: Payment) {

    }

    override fun onThreeDSecure(acsPage: AcsPage, isCascading: Boolean, payment: Payment) {

    }
}