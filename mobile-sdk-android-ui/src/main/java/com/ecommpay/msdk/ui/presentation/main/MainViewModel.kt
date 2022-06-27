package com.ecommpay.msdk.ui.presentation.main

import com.ecommpay.msdk.core.base.ErrorCode
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.core.domain.entities.init.SavedAccount
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.core.domain.interactors.init.InitDelegate
import com.ecommpay.msdk.core.domain.interactors.init.InitRequest
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.base.mvi.Reducer
import com.ecommpay.msdk.ui.base.mvi.TimeMachine
import com.ecommpay.msdk.ui.base.mvvm.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : BaseViewModel<MainScreenState, MainScreenUiEvent>() {
    override val reducer = MainReducer(MainScreenState.initial())
    private val initInteractor = PaymentActivity.msdkSession.getInitInteractor()

    override val state: StateFlow<MainScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<MainScreenState>
        get() = reducer.timeMachine

    init {
        //map payment info to core object
        val sdkPaymentInfo = PaymentInfo.create(
            projectId = PaymentActivity.paymentInfo.projectId,
            paymentId = PaymentActivity.paymentInfo.paymentId,
            paymentAmount = PaymentActivity.paymentInfo.paymentAmount,
            paymentCurrency = PaymentActivity.paymentInfo.paymentCurrency,
        )
        sdkPaymentInfo.customerId = PaymentActivity.paymentInfo.customerId
        sdkPaymentInfo.signature = PaymentActivity.paymentInfo.signature

        initInteractor.execute(
            request = InitRequest(
                paymentInfo = sdkPaymentInfo,
                recurrentInfo = null,
                threeDSecureInfo = null
            ),
            callback = object : InitDelegate {
                override fun onInitReceived(
                    paymentMethods: List<PaymentMethod>,
                    savedAccounts: List<SavedAccount>,
                ) {
                    sendEvent(MainScreenUiEvent.ShowData(paymentMethods))
                }

                override fun onError(code: ErrorCode, message: String) {
                }

                //Restore payment
                override fun onPaymentRestored(payment: Payment) {
                }
            }
        )
    }

    class MainReducer(initial: MainScreenState) :
        Reducer<MainScreenState, MainScreenUiEvent>(initial) {
        override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
            when (event) {
                is MainScreenUiEvent.ShowData -> setState(
                    oldState.copy(
                        isLoading = false,
                        data = event.data
                    )
                )
                is MainScreenUiEvent.ShowLoading -> setState(
                    oldState.copy(
                        isLoading = true,
                        data = emptyList()
                    )
                )
            }
        }
    }
}