package com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.customer

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
