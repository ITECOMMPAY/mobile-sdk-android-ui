package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData

sealed interface RecurrentViewIntents : ViewIntents {
    data class ChangeField(val newViewState: RecurrentViewState) : RecurrentViewIntents
    object ChangeCheckbox: RecurrentViewIntents
    object Exit : RecurrentViewIntents
    object ResetData : RecurrentViewIntents
    data class FillMockData(val mockData: RecurrentData): RecurrentViewIntents
}