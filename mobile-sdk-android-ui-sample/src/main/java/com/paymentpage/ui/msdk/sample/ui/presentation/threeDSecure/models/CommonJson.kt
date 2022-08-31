package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models

import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.ThreeDSecureInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonJson(
    @SerialName("secure_info")
    val threeDSecureInfo: ThreeDSecureInfo? = null
) {
    companion object {
        val default = CommonJson(
            threeDSecureInfo = ThreeDSecureInfo.default
        )
    }
}