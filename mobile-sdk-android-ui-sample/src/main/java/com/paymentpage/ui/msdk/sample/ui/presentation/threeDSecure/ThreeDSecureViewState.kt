package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState

data class ThreeDSecureViewState(
    val jsonThreeDSecureInfo: String,
    val isEnabledThreeDSecure: Boolean
) : ViewState