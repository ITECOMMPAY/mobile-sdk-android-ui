package com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val reorder: String? = null,
    @SerialName("preorder_purchase")
    val preorderPurchase: String? = null,
    @SerialName("preorder_date")
    val preorderDate: String? = null,
    @SerialName("challenge_indicator")
    val challengeIndicator: String? = null,
    @SerialName("challenge_window")
    val challengeWindow: String? = null,
    @SerialName("gift_card")
    val giftCard: GiftCard? = null
) {
    companion object {
        val default = Payment(
            reorder = "01",
            preorderPurchase = "01",
            preorderDate = "01-10-2020",
            challengeIndicator = "01",
            challengeWindow = "01",
            giftCard = GiftCard.default,
        )
    }
}
