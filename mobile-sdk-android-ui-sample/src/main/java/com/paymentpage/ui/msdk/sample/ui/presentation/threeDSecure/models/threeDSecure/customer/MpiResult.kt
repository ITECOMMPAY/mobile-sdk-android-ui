package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MpiResult(
    @SerialName("acs_operation_id")
    val acsOperationId: String? = null,
    @SerialName("authentication_flow")
    val authenticationFlow: String? = null,
    @SerialName("authentication_timestamp")
    val authenticationTimestamp: String? = null
) {
    companion object {
        val default = MpiResult(
            acsOperationId = "00000000-0005-5a5a-8000-016d3ea31d54",
            authenticationFlow = "01",
            authenticationTimestamp = "201812141050",
        )
    }
}