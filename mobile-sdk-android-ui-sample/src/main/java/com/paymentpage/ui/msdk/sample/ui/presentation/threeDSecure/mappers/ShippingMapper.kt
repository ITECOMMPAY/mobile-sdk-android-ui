package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureShippingInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.Shipping

internal fun Shipping.map(): ThreeDSecureShippingInfo =
    ThreeDSecureShippingInfo(
        type = type,
        deliveryTime = deliveryTime,
        deliveryEmail = deliveryEmail,
        addressUsageIndicator = addressUsageIndicator,
        addressUsage = addressUsage,
        city = city,
        country = country,
        address = address,
        postal = postal,
        regionCode = regionCode,
        nameIndicator = nameIndicator
    )