package com.paymentpage.ui.msdk.sample.data.entities.threeDSecure.payment

import kotlinx.serialization.Serializable

@Serializable
data class PaymentMerchantRisk(
    val payment: Payment? = null
) {
    companion object {
        val default = PaymentMerchantRisk(
            payment = Payment.default
        )
    }
}