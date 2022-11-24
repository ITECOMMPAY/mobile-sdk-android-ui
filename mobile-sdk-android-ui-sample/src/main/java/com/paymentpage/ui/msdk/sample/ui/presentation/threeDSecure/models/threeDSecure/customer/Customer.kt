package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val account: Account? = null,
    val shipping: Shipping? = null,
    @SerialName("mpi_result")
    val mpiResult: MpiResult? = null
)
