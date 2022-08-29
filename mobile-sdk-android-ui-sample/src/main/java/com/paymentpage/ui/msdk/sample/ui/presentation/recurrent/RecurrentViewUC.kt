package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.ui.presentation.base.BaseViewUseCase
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData

class RecurrentViewUC : BaseViewUseCase<RecurrentViewIntents, RecurrentViewState>() {
    override suspend fun init() {
        updateState(
            RecurrentViewState(
                recurrentData = ProcessRepository.recurrentData ?: RecurrentData.defaultData,
                isEnabledRecurrent = ProcessRepository.isEnabledRecurrent
            )
        )
    }

    override suspend fun reduce(viewIntent: RecurrentViewIntents) {
        when (viewIntent) {
            is RecurrentViewIntents.ChangeField -> {
                ProcessRepository.recurrentData = viewIntent.newViewState.recurrentData
                updateState(viewIntent.newViewState)
            }
            is RecurrentViewIntents.Exit -> {
                ProcessRepository.recurrentData = if (ProcessRepository.isEnabledRecurrent) viewState.value?.recurrentData else null
                launchAction(NavRoutes.Back)
            }
            is RecurrentViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value?.isEnabledRecurrent ?: false)
                ProcessRepository.isEnabledRecurrent = newValue
                updateState(viewState.value?.copy(isEnabledRecurrent = newValue))
            }
            is RecurrentViewIntents.ResetData -> {
                ProcessRepository.isEnabledRecurrent = false
                ProcessRepository.recurrentData = RecurrentData.defaultData
                updateState(RecurrentViewState(
                    recurrentData = RecurrentData.defaultData,
                    isEnabledRecurrent = false
                ))
            }
            is RecurrentViewIntents.FillMockData -> {
                ProcessRepository.isEnabledRecurrent = true
                ProcessRepository.recurrentData = viewIntent.mockData
                updateState(RecurrentViewState(
                    recurrentData = viewIntent.mockData,
                    isEnabledRecurrent = true
                ))
            }
        }
    }
}