package com.ecommpay.ui.msdk.sample.domain.ui.main


import com.ecommpay.msdk.ui.EcmpActionType
import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewIntents

class MainViewUC : BaseViewUC<MainViewIntents, MainViewState>(MainViewState()) {
    override suspend fun reduce(viewIntent: MainViewIntents) {
        when (viewIntent) {
            is MainViewIntents.ChangeField -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        paymentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData
                    )
                )
            }
            is MainViewIntents.SelectForcePaymentMethod -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        selectedForcePaymentMethodId = viewIntent.id,
                        paymentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData
                    )
                )
            }
            //Customization brand color and logo
            is MainViewIntents.ChangeCustomizationCheckbox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleCustomizationFields = !(viewState.value.isVisibleCustomizationFields)
                    )
                )
            }
            is MainViewIntents.SelectResourceImage -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        selectedResourceImageId = viewIntent.id,
                        paymentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData
                    )
                )
            }
            is MainViewIntents.SelectLocalImage -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        localImageUri = viewIntent.uri,
                        paymentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData,
                        selectedResourceImageId = -1
                    )
                )
            }
            //Custom mock mode
            is MainViewIntents.ChangeMockModeCheckbox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleMockModeType = !(viewState.value.isVisibleMockModeType)
                    )
                )
            }
            is MainViewIntents.SelectMockMode -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        selectedMockModeTypeId = viewIntent.id,
                        paymentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData
                    )
                )
            }
            //Other checkboxes
            is MainViewIntents.ChangeApiHostCheckBox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleApiHostFields = !(viewState.value.isVisibleApiHostFields)
                    )
                )
            }
            is MainViewIntents.ChangeGooglePayCheckBox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleGooglePayFields = !(viewState.value.isVisibleGooglePayFields)
                    )
                )
            }
            //Sale
            is MainViewIntents.Sale -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData =
                    com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData.copy(actionType = EcmpActionType.Sale)
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }
            //Tokenize
            is MainViewIntents.Tokenize -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData =
                    com.ecommpay.ui.msdk.sample.data.ProcessRepository.paymentData.copy(actionType = EcmpActionType.Tokenize)
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }
            is MainViewIntents.ThreeDSecure -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.ThreeDSecure))
            }
            is MainViewIntents.Recurrent -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.Recurrent))
            }
            is MainViewIntents.AdditionalFields -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.AdditionalFields))
            }
        }
    }
}