package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationField
import com.paymentpage.msdk.core.domain.entities.clarification.ClarificationFieldValue
import com.paymentpage.msdk.core.domain.entities.customer.CustomerField
import com.paymentpage.msdk.core.domain.entities.customer.CustomerFieldValue
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.payment.PaymentStatus
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.core.domain.interactors.pay.PayDelegate
import com.paymentpage.msdk.core.domain.interactors.pay.PayInteractor
import com.paymentpage.msdk.core.domain.interactors.pay.card.sale.NewCardSaleRequest
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

    fun saleCard(
        method: UiPaymentMethod,
        cvv: String,
        pan: String,
        year: Int,
        month: Int,
        cardHolder: String,
        saveCard: Boolean,
        customerFields: List<CustomerFieldValue> = emptyList()
    ) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        sendEvent(MainScreenUiEvent.SetPaymentMethod(method))
        val request = NewCardSaleRequest(
            cvv = cvv,
            pan = pan,
            year = year,
            month = month,
            cardHolder = cardHolder,
            saveCard = saveCard
        )
        request.customerFields = customerFields
        payInteractor.execute(request, this)
    }

    fun sendCustomerFields(customerFields: List<CustomerFieldValue>) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        payInteractor.sendCustomerFields(customerFields)
    }

    fun sendClarificationFields(clarificationFields: List<ClarificationFieldValue>) {
        sendEvent(MainScreenUiEvent.ShowLoading)
        payInteractor.sendClarificationFields(clarificationFields)
    }

    fun threeDSecureHandled() {
        sendEvent(MainScreenUiEvent.ShowLoading)
        payInteractor.threeDSecureHandled()
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
                        acsPage = null,
                        isCascading = false,
                        isLoading = true,
                    )
                )
                is MainScreenUiEvent.ShowError -> setState(
                    oldState.copy(
                        customerFields = emptyList(),
                        isLoading = false,
                        acsPage = null,
                        isCascading = false,
                        error = event.error,
                    )
                )
                is MainScreenUiEvent.ShowCustomerFields -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = event.customerFields,
                        clarificationFields = emptyList(),
                        acsPage = null,
                        isCascading = false,
                    )
                )
                is MainScreenUiEvent.ShowClarificationFields -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = emptyList(),
                        clarificationFields = event.clarificationFields,
                        acsPage = null,
                        isCascading = false,
                    )
                )
                is MainScreenUiEvent.ShowAcsPage -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = emptyList(),
                        clarificationFields = emptyList(),
                        acsPage = event.acsPage,
                        isCascading = event.isCascading,
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

    override fun onCompleteWithDecline(resultMessage: String?, payment: Payment) {

    }

    override fun onCompleteWithFail(isTryAgain: Boolean, resultMessage: String?, payment: Payment) {

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
        sendEvent(MainScreenUiEvent.ShowAcsPage(acsPage = acsPage, isCascading = isCascading))
    }
}