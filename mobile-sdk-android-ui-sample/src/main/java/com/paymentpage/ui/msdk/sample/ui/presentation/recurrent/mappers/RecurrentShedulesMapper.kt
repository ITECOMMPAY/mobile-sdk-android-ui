package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.mappers

import com.ecommpay.msdk.ui.EcmpRecurrentDataSchedule
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule

internal fun RecurrentDataSchedule.map() : EcmpRecurrentDataSchedule =
    EcmpRecurrentDataSchedule(
        date = date,
        amount = amount
    )