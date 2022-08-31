package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

import kotlinx.serialization.Serializable

@Serializable
data class CustomerShipping(
    val customer: Customer? = null
) {
    companion object {
        val default = CustomerShipping(
            customer = Customer(
                shipping = Shipping.default
            )
        )
    }
}
