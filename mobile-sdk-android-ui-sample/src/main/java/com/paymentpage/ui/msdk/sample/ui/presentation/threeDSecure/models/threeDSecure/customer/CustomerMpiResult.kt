package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

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
