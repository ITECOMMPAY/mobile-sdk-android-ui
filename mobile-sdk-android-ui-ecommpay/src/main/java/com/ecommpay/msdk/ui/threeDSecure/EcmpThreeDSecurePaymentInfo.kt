package com.ecommpay.msdk.ui.threeDSecure

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureGiftCardInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePaymentInfo

class EcmpThreeDSecurePaymentInfo(
    reorder: String? = null,
    preorderPurchase: String? = null,
    preorderDate: String? = null,
    challengeIndicator: String? = null,
    challengeWindow: String? = null,
    /**
     * object, which contains information about payment with prepaid card or gift card
     */
    giftCard: EcmpThreeDSecureGiftCardInfo? = null,
) : ThreeDSecurePaymentInfo(
    reorder = reorder,
    preorderPurchase = preorderPurchase,
    preorderDate = preorderDate,
    challengeIndicator = challengeIndicator,
    challengeWindow = challengeWindow,
    giftCard = giftCard
) {
    companion object {
        fun default() = EcmpThreeDSecurePaymentInfo()
    }
}

class EcmpThreeDSecureGiftCardInfo(
    amount: Int? = null,
    currency: String? = null,
    count: Int? = null,
) : ThreeDSecureGiftCardInfo(
    amount = amount,
    currency = currency,
    count = count
) {
    companion object {
        fun default() = EcmpThreeDSecureGiftCardInfo()
    }
}