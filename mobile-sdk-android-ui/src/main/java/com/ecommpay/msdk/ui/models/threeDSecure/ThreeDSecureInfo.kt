package com.ecommpay.msdk.ui.models.threeDSecure

import com.ecommpay.msdk.ui.models.threeDSecure.ThreeDSecureCustomerInfo
import com.ecommpay.msdk.ui.models.threeDSecure.ThreeDSecurePaymentInfo

/**
 *
 */
class ThreeDSecureInfo(
    /**
     * object with information about the customer [ThreeDSecureCustomerInfo][com.ecommpay.msdk.core.domain.entities.init.threeDSecure.ThreeDSecureCustomerInfo]
     */
    val threeDSecureCustomerInfo: ThreeDSecureCustomerInfo? = null,
    /**
     * information about the purchase details and indication of the preferable authentication flow [ThreeDSecurePaymentInfo][com.ecommpay.msdk.core.domain.entities.init.threeDSecure.ThreeDSecurePaymentInfo]
     */
    val threeDSecurePaymentInfo: ThreeDSecurePaymentInfo? = null
) {
    companion object Factory {
        fun default() = ThreeDSecureInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo(
            threeDSecureCustomerInfo = threeDSecureCustomerInfo?.map(),
            threeDSecurePaymentInfo = threeDSecurePaymentInfo?.map()
        )
}