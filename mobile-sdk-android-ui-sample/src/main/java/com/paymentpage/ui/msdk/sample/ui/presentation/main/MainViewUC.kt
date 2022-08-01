package com.paymentpage.ui.msdk.sample.ui.presentation.main

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.presentation.base.BaseViewUseCase
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData

class MainViewUC: BaseViewUseCase<MainViewIntents, MainViewState>(){
    override suspend fun reduce(viewIntent: MainViewIntents) {
        when (viewIntent) {
            is MainViewIntents.Init -> {
                updateState(
                    MainViewState(
                        paymentData = PaymentData.defaultPaymentData,
                        isVisibleApiHostFields = false,
                        isVisibleGooglePayFields = false,
                        isVisibleCustomizationFields = false,
                        selectedResourceImageId = -1,
                        isVisibleForcePaymentMethodFields = false,
                        selectedForcePaymentMethodId = -1,
                        localImageUri = null
                    )
                )
            }
            is MainViewIntents.ChangeField -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(viewState.value?.copy(
                    paymentData = viewIntent.paymentData
                ))
            }
            is MainViewIntents.ChangeForcePaymentMethodCheckbox -> {
                updateState(viewState.value?.copy(
                    isVisibleForcePaymentMethodFields = !(viewState.value?.isVisibleForcePaymentMethodFields ?: false)
                ))
            }
            is MainViewIntents.SelectForcePaymentMethod -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(viewState.value?.copy(
                    selectedForcePaymentMethodId = viewIntent.id,
                    paymentData = viewIntent.paymentData
                ))
            }
            is MainViewIntents.SelectResourceImage -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(viewState.value?.copy(
                    selectedResourceImageId = viewIntent.id,
                    paymentData = viewIntent.paymentData
                ))
            }
            is MainViewIntents.SelectLocalImage -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(viewState.value?.copy(
                    localImageUri = viewIntent.uri,
                    paymentData = viewIntent.paymentData,
                    selectedResourceImageId = -1
                ))
            }
            is MainViewIntents.ChangeCustomizationCheckbox -> {
                updateState(viewState.value?.copy(
                    isVisibleCustomizationFields = !(viewState.value?.isVisibleCustomizationFields ?: false)
                ))
            }
            is MainViewIntents.ChangeApiHostCheckBox -> {
                updateState(viewState.value?.copy(
                    isVisibleApiHostFields = !(viewState.value?.isVisibleApiHostFields ?: false)
                ))
            }
            is MainViewIntents.ChangeGooglePayCheckBox -> {
                updateState(viewState.value?.copy(
                    isVisibleGooglePayFields = !(viewState.value?.isVisibleGooglePayFields ?: false)
                ))
            }
            is MainViewIntents.Sale -> {
                launchAction(MainViewActions.Sale)
            }
        }
    }
}