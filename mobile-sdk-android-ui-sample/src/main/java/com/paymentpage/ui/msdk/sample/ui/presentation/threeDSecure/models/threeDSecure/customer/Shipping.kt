package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Shipping(
    val type: String? = null,
    @SerialName("delivery_time")
    val deliveryTime: String? = null,
    @SerialName("delivery_email")
    val deliveryEmail: String? = null,
    @SerialName("address_usage_indicator")
    val addressUsageIndicator: String? = null,
    @SerialName("address_usage")
    val addressUsage: String? = null,
    val city: String? = null,
    val country: String? = null,
    val address: String? = null,
    val postal: String? = null,
    @SerialName("region_code")
    val regionCode: String? = null,
    @SerialName("name_indicator")
    val nameIndicator: String? = null
) {
    companion object {
        val default = Shipping(
            type = "01",
            deliveryTime = "01",
            deliveryEmail = "test@gmail.com",
            addressUsageIndicator = "01",
            addressUsage = "01-10-2019",
            city = "Moscow",
            country = "RU",
            address = "Lenina street 12",
            postal = "109111",
            regionCode = "MOW",
            nameIndicator = "01",
        )
    }
}
