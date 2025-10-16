package com.mglwallet.ui.msdk.sample.domain.ui.recipient

import com.mglwallet.ui.msdk.sample.domain.entities.RecipientData
import com.mglwallet.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface RecipientViewIntents : ViewIntents {
    data class ChangeField(val recipientData: RecipientData) : RecipientViewIntents
    object ChangeCheckbox : RecipientViewIntents
    object Exit : RecipientViewIntents
    object ResetData : RecipientViewIntents
    object FillMockData : RecipientViewIntents
}