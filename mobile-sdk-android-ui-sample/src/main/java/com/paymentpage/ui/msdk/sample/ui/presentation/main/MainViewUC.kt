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
                        isVisibleMockModeType = false,
                        selectedResourceImageId = -1,
                        isVisibleForcePaymentMethodFields = false,
                        selectedForcePaymentMethodId = -1,
                        selectedMockModeTypeId = 0,
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
            //Force payment method
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
            //Customization brand color and logo
            is MainViewIntents.ChangeCustomizationCheckbox -> {
                updateState(viewState.value?.copy(
                    isVisibleCustomizationFields = !(viewState.value?.isVisibleCustomizationFields ?: false)
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
            //Custom mock mode
            is MainViewIntents.ChangeMockModeCheckbox -> {
                updateState(viewState.value?.copy(
                    isVisibleMockModeType = !(viewState.value?.isVisibleMockModeType ?: false)
                ))
            }
            is MainViewIntents.SelectMockMode -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(viewState.value?.copy(
                    selectedMockModeTypeId = viewIntent.id,
                    paymentData = viewIntent.paymentData
                ))
            }
            //Other checkboxes
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
            //Sale
            is MainViewIntents.Sale -> {
                launchAction(MainViewActions.Sale)
            }
        }
    }
}