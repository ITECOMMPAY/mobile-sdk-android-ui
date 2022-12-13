package com.paymentpage.ui.msdk.sample.domain.ui.sample

import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewActions

sealed interface SampleViewActions: ViewActions {
    object StartPaymentSDK : SampleViewActions
    data class CopyInClipboard(val text: String, val textToast: String) : SampleViewActions
}