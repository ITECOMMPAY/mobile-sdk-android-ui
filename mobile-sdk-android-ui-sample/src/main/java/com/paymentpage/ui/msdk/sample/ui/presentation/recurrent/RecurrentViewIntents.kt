package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewIntents

sealed interface RecurrentViewIntents : ViewIntents {
    object Init : RecurrentViewIntents
    data class ChangeField(val viewData: RecurrentViewData?) : RecurrentViewIntents
}