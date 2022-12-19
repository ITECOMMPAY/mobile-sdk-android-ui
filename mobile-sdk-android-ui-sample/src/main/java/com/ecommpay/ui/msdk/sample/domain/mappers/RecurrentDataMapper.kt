package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.EcmpRecurrentData
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData

internal fun RecurrentData.map(): EcmpRecurrentData? = if (register != null)
    EcmpRecurrentData(
        register = register,
        type = type,
        expiryDay = expiryDay,
        expiryMonth = expiryMonth,
        expiryYear = expiryYear,
        period = period,
        time = time,
        startDate = startDate,
        scheduledPaymentID = scheduledPaymentID,
        amount = amount,
        schedule = schedule?.map { it.map() }
    ) else null