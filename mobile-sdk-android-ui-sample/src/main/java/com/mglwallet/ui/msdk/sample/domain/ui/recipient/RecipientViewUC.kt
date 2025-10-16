package com.mglwallet.ui.msdk.sample.domain.ui.recipient

import com.mglwallet.ui.msdk.sample.data.ProcessRepository
import com.mglwallet.ui.msdk.sample.domain.entities.RecipientData
import com.mglwallet.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.mglwallet.ui.msdk.sample.domain.ui.base.back

class RecipientViewUC : BaseViewUC<RecipientViewIntents, RecipientViewState>(RecipientViewState()) {

    init {
        updateState(
            newState = RecipientViewState(
                recipientData = ProcessRepository.recipientData ?: RecipientData(),
                isEnabledRecipient = ProcessRepository.isEnabledRecipient
            )
        )
    }

    override suspend fun reduce(viewIntent: RecipientViewIntents) {
        when (viewIntent) {
            is RecipientViewIntents.ChangeField -> {
                ProcessRepository.recipientData = viewIntent.recipientData
                updateState(viewState.value.copy(recipientData = viewIntent.recipientData))
            }
            is RecipientViewIntents.Exit -> {
                ProcessRepository.recipientData = if (ProcessRepository.isEnabledRecipient) viewState.value.recipientData else null
                back()
            }
            is RecipientViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledRecipient)
                ProcessRepository.isEnabledRecipient = newValue
                updateState(viewState.value.copy(isEnabledRecipient = newValue))
            }
            is RecipientViewIntents.ResetData -> {
                ProcessRepository.isEnabledRecipient = false
                ProcessRepository.recipientData = null
                updateState(
                    RecipientViewState(
                        recipientData = RecipientData(),
                        isEnabledRecipient = false
                    )
                )
            }
            is RecipientViewIntents.FillMockData -> {
                ProcessRepository.isEnabledRecipient = true
                ProcessRepository.recipientData = RecipientData.mockData
                updateState(
                    RecipientViewState(
                        recipientData = RecipientData.mockData,
                        isEnabledRecipient = ProcessRepository.isEnabledRecipient
                    )
                )
            }
        }
    }
}