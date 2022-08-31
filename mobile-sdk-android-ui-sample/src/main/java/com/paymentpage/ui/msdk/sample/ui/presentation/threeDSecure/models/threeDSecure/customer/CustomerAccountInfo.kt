package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

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
