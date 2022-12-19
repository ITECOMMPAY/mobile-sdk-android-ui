package com.ecommpay.ui.msdk.sample.domain.ui.sample

import com.ecommpay.ui.msdk.sample.domain.ui.base.MessageUI
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewState

data class SampleViewState(
    val uiMessage: MessageUI = MessageUI.Empty
) : ViewState
