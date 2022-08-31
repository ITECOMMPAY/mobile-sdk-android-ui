package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureShippingInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.Shipping

internal fun Shipping.map(): EcmpThreeDSecureShippingInfo =
    EcmpThreeDSecureShippingInfo(
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