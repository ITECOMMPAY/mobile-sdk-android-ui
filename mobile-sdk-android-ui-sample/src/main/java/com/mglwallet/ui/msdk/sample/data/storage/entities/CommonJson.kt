package com.mglwallet.ui.msdk.sample.data.storage.entities

import com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.ThreeDSecureInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonJson(
    @SerialName("secure_info")
    val threeDSecureInfo: ThreeDSecureInfoModel? = null
) {
    companion object {
        val default = CommonJson(
            threeDSecureInfo = ThreeDSecureInfoModel.default
        )
    }
}