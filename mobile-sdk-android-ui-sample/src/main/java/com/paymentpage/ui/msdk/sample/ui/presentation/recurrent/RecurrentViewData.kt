package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule

data class RecurrentViewData(
    val recurrentData: RecurrentData?,
    val schedules: List<RecurrentDataSchedule>?
) : ViewState
