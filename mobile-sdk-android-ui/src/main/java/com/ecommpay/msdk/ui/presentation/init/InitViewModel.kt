package com.ecommpay.msdk.ui.presentation.init

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
import com.ecommpay.msdk.ui.model.init.UIPaymentMethod
import kotlinx.coroutines.flow.StateFlow

internal class InitViewModel : BaseViewModel<InitScreenState, InitScreenUiEvent>() {
    override val reducer = InitReducer(InitScreenState.initial())
    private val initInteractor = PaymentActivity.msdkSession.getInitInteractor()

    override val state: StateFlow<InitScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<InitScreenState>
        get() = reducer.timeMachine

    init {
        loadInit()
    }

    private fun loadInit() {
        //map payment info to core object
        val paymentInfo = PaymentActivity.paymentOptions ?: return
        val sdkPaymentInfo = PaymentInfo.create(
            projectId = paymentInfo.projectId,
            paymentId = paymentInfo.paymentId,
            paymentAmount = paymentInfo.paymentAmount,
            paymentCurrency = paymentInfo.paymentCurrency,
        )
        sdkPaymentInfo.customerId = paymentInfo.customerId
        sdkPaymentInfo.signature = paymentInfo.signature

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
                    sendEvent(
                        InitScreenUiEvent.InitLoaded(
                            paymentMethods.map {
                                UIPaymentMethod(
                                    code = it.code,
                                    name = it.name ?: "",
                                    iconUrl = it.iconUrl
                                )
                            }
                        )
                    )
                }

                override fun onError(code: ErrorCode, message: String) {
                }

                //Restore payment
                override fun onPaymentRestored(payment: Payment) {
                }
            }
        )
    }

    class InitReducer(initial: InitScreenState) :
        Reducer<InitScreenState, InitScreenUiEvent>(initial) {
        override fun reduce(oldState: InitScreenState, event: InitScreenUiEvent) {
            when (event) {
                is InitScreenUiEvent.InitLoaded -> setState(
                    oldState.copy(
                        isInitLoaded = true,
                        data = event.data
                    )
                )
                is InitScreenUiEvent.ShowLoading -> setState(
                    oldState.copy(
                        isInitLoaded = false,
                        data = emptyList()
                    )
                )
            }
        }
    }
}