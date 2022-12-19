package com.ecommpay.ui.msdk.sample.domain.ui.threeDSecure

import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewState

data class ThreeDSecureViewState(
    val jsonThreeDSecureInfo: String = "",
    val isEnabledThreeDSecure: Boolean = false
) : ViewState