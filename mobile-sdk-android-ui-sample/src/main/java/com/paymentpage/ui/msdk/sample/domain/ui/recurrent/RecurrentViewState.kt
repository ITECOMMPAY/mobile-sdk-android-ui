package com.paymentpage.ui.msdk.sample.domain.ui.recurrent

import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewState
import com.paymentpage.ui.msdk.sample.domain.entities.RecurrentData

data class RecurrentViewState(
    val recurrentData: RecurrentData = RecurrentData(),
    val isEnabledRecurrent: Boolean = false
) : ViewState
