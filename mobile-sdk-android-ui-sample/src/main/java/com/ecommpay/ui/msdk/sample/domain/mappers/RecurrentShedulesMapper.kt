package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.EcmpRecurrentDataSchedule
import com.paymentpage.msdk.core.domain.entities.RecurrentInfoSchedule
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentDataSchedule

internal fun RecurrentDataSchedule.map(): EcmpRecurrentDataSchedule =
    EcmpRecurrentDataSchedule(
        date = date,
        amount = amount
    )