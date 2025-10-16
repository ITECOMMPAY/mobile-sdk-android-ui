package com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.customer

import kotlinx.serialization.Serializable

@Serializable
data class CustomerMpiResult(
    val customer: Customer? = null
) {
    companion object {
        val default = CustomerMpiResult(
            customer = Customer(
                mpiResult = MpiResult.default
            )
        )
    }
}
