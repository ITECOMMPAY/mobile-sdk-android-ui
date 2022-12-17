package com.ecommpay.ui.msdk.sample.domain.ui.recurrent

import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.base.back

class RecurrentViewUC : BaseViewUC<RecurrentViewIntents, RecurrentViewState>(RecurrentViewState()) {

    init {
        updateState(
            newState = RecurrentViewState(
                recurrentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData,
                isEnabledRecurrent = com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledRecurrent
            )
        )
    }

    override suspend fun reduce(viewIntent: RecurrentViewIntents) {
        when (viewIntent) {
            is RecurrentViewIntents.ChangeField -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData = viewIntent.recurrentData.copy(register = true)
                updateState(viewState.value.copy(recurrentData = com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData))
            }
            is RecurrentViewIntents.Exit -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData =
                    if (com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledRecurrent) viewState.value.recurrentData else RecurrentData()
                back()
            }
            is RecurrentViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledRecurrent)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledRecurrent = newValue
                updateState(viewState.value.copy(isEnabledRecurrent = newValue))
            }
            is RecurrentViewIntents.ResetData -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledRecurrent = false
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData = RecurrentData()
                updateState(
                    RecurrentViewState(
                        recurrentData = RecurrentData(),
                        isEnabledRecurrent = false
                    )
                )
            }
            is RecurrentViewIntents.FillMockData -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledRecurrent = true
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.recurrentData = viewIntent.mockData
                updateState(
                    RecurrentViewState(
                        recurrentData = viewIntent.mockData,
                        isEnabledRecurrent = true
                    )
                )
            }
        }
    }
}