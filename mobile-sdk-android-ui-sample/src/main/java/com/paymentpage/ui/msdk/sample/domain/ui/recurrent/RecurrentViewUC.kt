package com.paymentpage.ui.msdk.sample.domain.ui.recurrent

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.domain.entities.RecurrentData
import com.paymentpage.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.base.back

class RecurrentViewUC : BaseViewUC<RecurrentViewIntents, RecurrentViewState>(RecurrentViewState()) {

    init {
        updateState(
            newState = RecurrentViewState(
                recurrentData = ProcessRepository.recurrentData,
                isEnabledRecurrent = ProcessRepository.isEnabledRecurrent
            )
        )
    }

    override suspend fun reduce(viewIntent: RecurrentViewIntents) {
        when (viewIntent) {
            is RecurrentViewIntents.ChangeField -> {
                ProcessRepository.recurrentData = viewIntent.recurrentData
                updateState(viewState.value.copy(recurrentData = viewIntent.recurrentData))
            }
            is RecurrentViewIntents.Exit -> {
                ProcessRepository.recurrentData =
                    if (ProcessRepository.isEnabledRecurrent) viewState.value.recurrentData else RecurrentData()
                back()
            }
            is RecurrentViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledRecurrent)
                ProcessRepository.isEnabledRecurrent = newValue
                updateState(viewState.value.copy(isEnabledRecurrent = newValue))
            }
            is RecurrentViewIntents.ResetData -> {
                ProcessRepository.isEnabledRecurrent = false
                ProcessRepository.recurrentData = RecurrentData()
                updateState(
                    RecurrentViewState(
                        recurrentData = RecurrentData(),
                        isEnabledRecurrent = false
                    )
                )
            }
            is RecurrentViewIntents.FillMockData -> {
                ProcessRepository.isEnabledRecurrent = true
                ProcessRepository.recurrentData = viewIntent.mockData
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