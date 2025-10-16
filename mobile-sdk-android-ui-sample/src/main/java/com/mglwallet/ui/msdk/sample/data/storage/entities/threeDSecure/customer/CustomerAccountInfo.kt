package com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.customer

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
