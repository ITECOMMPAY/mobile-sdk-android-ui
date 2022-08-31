package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData

data class RecurrentViewState(
    val recurrentData: RecurrentData,
    val isEnabledRecurrent: Boolean
) : ViewState
