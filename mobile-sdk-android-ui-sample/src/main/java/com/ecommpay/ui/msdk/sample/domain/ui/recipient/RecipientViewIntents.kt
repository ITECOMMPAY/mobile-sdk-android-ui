package com.ecommpay.ui.msdk.sample.domain.ui.recipient

import com.ecommpay.ui.msdk.sample.domain.entities.RecipientData
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface RecipientViewIntents : ViewIntents {
    data class ChangeField(val recipientData: RecipientData) : RecipientViewIntents
    object ChangeCheckbox : RecipientViewIntents
    object Exit : RecipientViewIntents
    object ResetData : RecipientViewIntents
    object FillMockData : RecipientViewIntents
}