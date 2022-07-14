package com.ecommpay.msdk.ui.models.threeDSecure


/**
 * information about the purchase details and indication of the preferable authentication flow
 */
class ThreeDSecurePaymentInfo(
    val reorder: String? = null,
    val preorderPurchase: String? = null,
    val preorderDate: String? = null,
    val challengeIndicator: String? = null,
    val challengeWindow: String? = null,
    /**
     * object, which contains information about payment with prepaid card or gift card
     */
    val giftCard: ThreeDSecureGiftCardInfo? = null
) {
    companion object {
        fun default() = ThreeDSecurePaymentInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecurePaymentInfo(
            reorder = reorder,
            preorderPurchase = preorderPurchase,
            preorderDate = preorderDate,
            challengeIndicator = challengeIndicator,
            challengeWindow = challengeWindow
        )
}

class ThreeDSecureGiftCardInfo(
    val amount: Int? = null,
    val currency: String? = null,
    val count: Int? = null
) {
    companion object {
        fun default() = ThreeDSecureGiftCardInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureGiftCardInfo(
            amount = amount,
            currency = currency,
            count = count
        )
}