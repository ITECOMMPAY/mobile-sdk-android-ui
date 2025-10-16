package com.mglwallet.msdk.ui.threeDSecure

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo

class EcmpThreeDSecureInfo(
    val ecmpThreeDSecureCustomerInfo: EcmpThreeDSecureCustomerInfo? = null,
    val ecmpThreeDSecurePaymentInfo: EcmpThreeDSecurePaymentInfo? = null
) : ThreeDSecureInfo(
    threeDSecureCustomerInfo = ecmpThreeDSecureCustomerInfo,
    threeDSecurePaymentInfo = ecmpThreeDSecurePaymentInfo
) {
    companion object {
        fun default() = EcmpThreeDSecureInfo()
    }
}