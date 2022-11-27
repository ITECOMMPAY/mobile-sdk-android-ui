package com.paymentpage.ui.msdk.sample.domain.ui.sample

import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI
import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface SampleViewIntents : ViewIntents {
    data class ShowMessage(val uiMessage: MessageUI) : SampleViewIntents
    object StartPaymentSDK : SampleViewIntents
}
