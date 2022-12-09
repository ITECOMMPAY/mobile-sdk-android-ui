package com.paymentpage.ui.msdk.sample.data.entities.threeDSecure.customer

import kotlinx.serialization.Serializable

@Serializable
data class CustomerAccountInfo(
    val customer: Customer? = null
) {
    companion object {
        val default = CustomerAccountInfo(
            customer = Customer(
                account = Account.default
            )
        )
    }
}
