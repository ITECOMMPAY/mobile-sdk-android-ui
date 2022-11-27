package com.paymentpage.ui.msdk.sample.domain.ui.recurrent

import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewIntents
import com.paymentpage.ui.msdk.sample.domain.entities.RecurrentData

sealed interface RecurrentViewIntents : ViewIntents {
    data class ChangeField(val recurrentData: RecurrentData) : RecurrentViewIntents
    object ChangeCheckbox: RecurrentViewIntents
    object Exit : RecurrentViewIntents
    object ResetData : RecurrentViewIntents
    data class FillMockData(val mockData: RecurrentData): RecurrentViewIntents
}