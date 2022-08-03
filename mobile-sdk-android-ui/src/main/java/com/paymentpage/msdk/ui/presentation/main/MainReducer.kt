package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.ui.base.mvi.Reducer

internal class MainReducer(initial: MainScreenState) :
    Reducer<MainScreenState, MainScreenUiEvent>(initial) {
    override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
        when (event) {
            is MainScreenUiEvent.ShowLoading -> setState(
                oldState.copy(
                    customerFields = emptyList(),
                    clarificationFields = emptyList(),
                    acsPageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isLoading = true,
                )
            )
            is MainScreenUiEvent.ShowError -> setState(
                oldState.copy(
                    customerFields = emptyList(),
                    isLoading = false,
                    acsPageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    error = event.error,
                )
            )
            is MainScreenUiEvent.ShowCustomerFields -> setState(
                oldState.copy(
                    isLoading = event.customerFields.none { !it.isHidden },
                    customerFields = event.customerFields,
                    clarificationFields = emptyList(),
                    acsPageState = null,
                    finalPaymentState = null,
                    apsPageState = null
                )
            )
            is MainScreenUiEvent.ShowClarificationFields -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    clarificationFields = event.clarificationFields,
                    acsPageState = null,
                    finalPaymentState = null,
                    apsPageState = null
                )
            )
            is MainScreenUiEvent.ShowAcsPage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    clarificationFields = emptyList(),
                    acsPageState = AcsPageState(
                        acsPage = event.acsPage,
                        isCascading = event.isCascading
                    ),
                    finalPaymentState = null,
                    apsPageState = null
                )
            )
            is MainScreenUiEvent.ShowSuccessPage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    clarificationFields = emptyList(),
                    acsPageState = null,
                    finalPaymentState = FinalPaymentState.Success,
                    apsPageState = null
                )
            )
            is MainScreenUiEvent.ShowDeclinePage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    clarificationFields = emptyList(),
                    acsPageState = null,
                    apsPageState = null,
                    finalPaymentState = FinalPaymentState.Decline(
                        paymentMessage = event.paymentMessage,
                        isTryAgain = event.isTryAgain
                    ),
                )
            )
            is MainScreenUiEvent.SetCurrentMethod -> setState(
                oldState.copy(currentMethod = event.method)
            )
            is MainScreenUiEvent.SetPayment -> setState(
                oldState.copy(payment = event.payment)
            )
            is MainScreenUiEvent.ShowApsPage -> setState(
                oldState.copy(apsPageState = ApsPageState(apsMethod = event.apsMethod))
            )
        }
    }
}