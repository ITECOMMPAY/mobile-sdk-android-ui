package com.paymentpage.msdk.ui.presentation.init


import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.core.domain.entities.init.SavedAccount
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.msdk.core.domain.interactors.init.InitDelegate
import com.paymentpage.msdk.core.domain.interactors.init.InitInteractor
import com.paymentpage.msdk.core.domain.interactors.init.InitRequest
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.base.mvi.Reducer
import com.paymentpage.msdk.ui.base.mvi.TimeMachine
import com.paymentpage.msdk.ui.base.mvvm.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

internal class InitViewModel(
    private val initInteractor: InitInteractor,
    private val paymentInfo: PaymentInfo,
    private val recurrentInfo: RecurrentInfo?,
    private val threeDSecureInfo: ThreeDSecureInfo?
) :
    BaseViewModel<InitScreenState, InitScreenUiEvent>() {
    override val reducer = InitReducer(InitScreenState.initial())

    override val state: StateFlow<InitScreenState>
        get() = reducer.state

    override val timeMachine: TimeMachine<InitScreenState>
        get() = reducer.timeMachine

    init {
        loadInit()
    }

    private fun loadInit() {
        initInteractor.execute(
            request = InitRequest(
                paymentInfo = paymentInfo,
                recurrentInfo = recurrentInfo,
                threeDSecureInfo = threeDSecureInfo
            ),
            callback = object : InitDelegate {
                override fun onInitReceived(
                    paymentMethods: List<PaymentMethod>,
                    savedAccounts: List<SavedAccount>,
                ) = sendEvent(InitScreenUiEvent.InitLoaded)

                override fun onError(code: ErrorCode, message: String) =
                    sendEvent(InitScreenUiEvent.ShowError(ErrorResult(code, message)))

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
                    oldState.copy(isInitLoaded = true)
                )
                is InitScreenUiEvent.ShowLoading -> setState(
                    oldState.copy(isInitLoaded = false)
                )
                is InitScreenUiEvent.ShowError -> setState(
                    oldState.copy(
                        isInitLoaded = false,
                        error = event.error
                    )
                )
            }
        }
    }
}