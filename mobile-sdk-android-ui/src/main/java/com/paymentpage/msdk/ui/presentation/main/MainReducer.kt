package com.paymentpage.msdk.ui.presentation.main

import com.paymentpage.msdk.ui.base.mvi.Reducer

internal class MainReducer(initial: MainScreenState) :
    Reducer<MainScreenState, MainScreenUiEvent>(initial) {
    override fun reduce(oldState: MainScreenState, event: MainScreenUiEvent) {
        when (event) {
            is MainScreenUiEvent.ShowLoading -> setState(
                oldState.copy(
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isLoading = true,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowDeleteCardLoading -> setState(
                oldState.copy(
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isLoading = null,
                    isDeleteCardLoading = event.isLoading,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowError -> setState(
                oldState.copy(
                    customerFields = emptyList(),
                    request = null,
                    isLoading = false,
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    error = event.error,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowCustomerFields -> setState(
                oldState.copy(
                    isLoading = event.customerFields.none { !it.isHidden },
                    customerFields = event.customerFields,
                    request = event.request,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowClarificationFields -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    clarificationFields = event.clarificationFields,
                    request = null,
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowThreeDSecurePage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = ThreeDSecurePageState(
                        threeDSecurePage = event.threeDSecurePage,
                        isCascading = event.isCascading
                    ),
                    finalPaymentState = null,
                    apsPageState = null,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowSuccessPage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    finalPaymentState = FinalPaymentState.Success,
                    apsPageState = null,
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowDeclinePage -> setState(
                oldState.copy(
                    isLoading = false,
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    apsPageState = null,
                    finalPaymentState = FinalPaymentState.Decline(
                        paymentMessage = event.paymentMessage,
                        isTryAgain = event.isTryAgain
                    ),
                    isDeleteCardLoading = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.ShowApsPage -> setState(
                oldState.copy(
                    apsPageState = ApsPageState(apsMethod = event.apsMethod),
                    customerFields = emptyList(),
                    request = null,
                    isTryAgain = null
                )
            )
            is MainScreenUiEvent.TryAgain -> setState(
                oldState.copy(
                    isTryAgain = true,
                    isLoading = false,
                    customerFields = emptyList(),
                    request = null,
                    clarificationFields = emptyList(),
                    threeDSecurePageState = null,
                    finalPaymentState = null,
                    apsPageState = null,
                    isDeleteCardLoading = null,
                )
            )
        }
    }
}