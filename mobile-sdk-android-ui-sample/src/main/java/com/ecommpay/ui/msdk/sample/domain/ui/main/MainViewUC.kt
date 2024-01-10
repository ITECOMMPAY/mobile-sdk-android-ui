package com.ecommpay.ui.msdk.sample.domain.ui.main


import com.ecommpay.msdk.ui.EcmpActionType
import com.ecommpay.msdk.ui.EcmpScreenDisplayMode
import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.MainHostScreens
import com.ecommpay.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewIntents

class MainViewUC : BaseViewUC<MainViewIntents, MainViewState>(MainViewState()) {
    override suspend fun reduce(viewIntent: MainViewIntents) {
        when (viewIntent) {
            is MainViewIntents.ChangeField -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        paymentData = ProcessRepository.paymentData
                    )
                )
            }

            is MainViewIntents.SelectForcePaymentMethod -> {
                ProcessRepository.paymentData = viewIntent.paymentData
                updateState(
                    viewState.value.copy(
                        selectedForcePaymentMethodId = viewIntent.id,
                        paymentData = ProcessRepository.paymentData
                    )
                )
            }
            //Customization brand color and logo
            is MainViewIntents.ChangeThemeCheckbox -> {
                ProcessRepository.isDarkTheme = !(viewState.value.isDarkTheme)
                updateState(
                    viewState.value.copy(isDarkTheme = ProcessRepository.isDarkTheme)
                )
            }

            is MainViewIntents.ChangeCustomizationCheckbox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleCustomizationFields = !(viewState.value.isVisibleCustomizationFields)
                    )
                )
            }

            is MainViewIntents.SelectResourceImage -> {
                ProcessRepository.bitmap = viewIntent.bitmap
                updateState(
                    viewState.value.copy(
                        selectedResourceImageId = viewIntent.id,
                        bitmap = viewIntent.bitmap
                    )
                )
            }

            is MainViewIntents.SelectLocalImage -> {
                ProcessRepository.bitmap = viewIntent.bitmap
                updateState(
                    viewState.value.copy(
                        localImageUri = viewIntent.uri,
                        selectedResourceImageId = -1,
                        bitmap = viewIntent.bitmap
                    )
                )
            }

            is MainViewIntents.ChangeBrandColor -> {
                ProcessRepository.brandColor = viewIntent.brandColor
                updateState(
                    viewState.value.copy(
                        brandColor = viewIntent.brandColor
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
                ProcessRepository.mockModeType = viewIntent.mockModeType
                updateState(
                    viewState.value.copy(
                        selectedMockModeType = ProcessRepository.mockModeType
                    )
                )
            }
            //custom screen display mode
            is MainViewIntents.ChangeScreenDisplayModeCheckbox -> {
                updateState(
                    viewState.value.copy(
                        isVisibleScreenDisplayMode = !(viewState.value.isVisibleScreenDisplayMode)
                    )
                )
            }

            is MainViewIntents.SelectScreenDisplayMode -> {
                var changedList = viewState.value.selectedScreenDisplayModes.toMutableList()
                if (viewState.value.selectedScreenDisplayModes.contains(viewIntent.screenDisplayMode) &&
                    viewState.value.selectedScreenDisplayModes.size != 1
                )
                    changedList.remove(viewIntent.screenDisplayMode)
                else if (viewIntent.screenDisplayMode == EcmpScreenDisplayMode.DEFAULT)
                    changedList = mutableListOf(EcmpScreenDisplayMode.DEFAULT)
                else if (!viewState.value.selectedScreenDisplayModes.contains(EcmpScreenDisplayMode.DEFAULT))
                    changedList.add(viewIntent.screenDisplayMode)
                else
                    changedList = mutableListOf(viewIntent.screenDisplayMode)
                ProcessRepository.screenDisplayModes = changedList
                updateState(
                    viewState.value.copy(
                        selectedScreenDisplayModes = changedList.toList(),
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

            is MainViewIntents.ChangeHideScanningCardsCheckbox -> {
                ProcessRepository.hideScanningCards = !(viewState.value.hideScanningCards)
                updateState(
                    viewState.value.copy(
                        hideScanningCards = ProcessRepository.hideScanningCards
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
                ProcessRepository.actionType = EcmpActionType.Sale
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }
            //Auth
            is MainViewIntents.Auth -> {
                ProcessRepository.actionType = EcmpActionType.Auth
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }
            //Verify
            is MainViewIntents.Verify -> {
                ProcessRepository.actionType = EcmpActionType.Verify
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }
            //Tokenize
            is MainViewIntents.Tokenize -> {
                ProcessRepository.actionType = EcmpActionType.Tokenize
                pushExternalIntent(SampleViewIntents.StartPaymentSDK)
            }

            is MainViewIntents.ThreeDSecure -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.ThreeDSecure))
            }

            is MainViewIntents.Recurrent -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.Recurrent))
            }

            is MainViewIntents.Recipient -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.Recipient))
            }

            is MainViewIntents.AdditionalFields -> {
                pushExternalIntent(NavigationViewIntents.Navigate(to = MainHostScreens.AdditionalFields))
            }

            is MainViewIntents.ChangeStoredCardType -> {
                val stringValue = viewIntent.storedCardType
                val intValue = if (stringValue.length == 1 &&
                    stringValue.all { it.isDigit() } &&
                    stringValue.toInt() in 0..6
                ) {
                    try {
                        stringValue.toInt()
                    } catch (ex: Exception) {
                        null
                    }
                } else {
                    null
                }
                ProcessRepository.storedCardType = intValue
                updateState(
                    viewState.value.copy(
                        storedCardType = intValue
                    )
                )
            }
        }
    }
}