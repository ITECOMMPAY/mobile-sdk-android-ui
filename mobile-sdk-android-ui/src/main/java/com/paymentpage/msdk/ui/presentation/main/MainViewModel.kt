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
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
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
        method: UIPaymentMethod,
        accountId: Long,
        cvv: String,
        customerFields: List<CustomerFieldValue> = emptyList()
    ) {
        sendEvent(MainScreenUiEvent.ShowLoading(method))
        val request = SavedCardSaleRequest(cvv = cvv, accountId = accountId)
        request.customerFields = customerFields
        payInteractor.execute(request, this)
    }

    init {

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
                        isLoading = true,
                        method = event.method
                    )
                )
                is MainScreenUiEvent.ShowError -> setState(
                    oldState.copy(
                        customerFields = emptyList(),
                        isLoading = false,
                        error = event.error,
                        method = null
                    )
                )
                is MainScreenUiEvent.ShowCustomerFields -> setState(
                    oldState.copy(
                        isLoading = false,
                        customerFields = event.customerFields
                    )
                )
            }
        }
    }

    override fun onClarificationFields(
        clarificationFields: List<ClarificationField>,
        payment: Payment
    ) {
        TODO("Not yet implemented")
    }

    override fun onCompleteWithDecline(payment: Payment) {
        TODO("Not yet implemented")
    }

    override fun onCompleteWithFail(status: String?, payment: Payment) {
        TODO("Not yet implemented")
    }

    override fun onCompleteWithSuccess(payment: Payment) {
        TODO("Not yet implemented")
    }

    override fun onCustomerFields(customerFields: List<CustomerField>) {
        sendEvent(MainScreenUiEvent.ShowCustomerFields(customerFields = customerFields))
    }

    override fun onError(code: ErrorCode, message: String) {
        TODO("Not yet implemented")
    }

    override fun onPaymentCreated() {
        TODO("Not yet implemented")
    }

    override fun onStatusChanged(status: PaymentStatus, payment: Payment) {
        TODO("Not yet implemented")
    }

    override fun onThreeDSecure(acsPage: AcsPage, isCascading: Boolean, payment: Payment) {
        TODO("Not yet implemented")
    }
}