package com.paymentpage.ui.msdk.sample.domain.ui.sample

import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewActions

sealed interface SampleViewActions: ViewActions {
    object StartPaymentSDK : SampleViewActions
}