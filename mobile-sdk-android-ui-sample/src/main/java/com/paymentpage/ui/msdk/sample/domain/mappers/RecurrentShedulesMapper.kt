package com.paymentpage.ui.msdk.sample.domain.mappers

import com.paymentpage.msdk.core.domain.entities.RecurrentInfoSchedule
import com.paymentpage.ui.msdk.sample.domain.entities.RecurrentDataSchedule

internal fun RecurrentDataSchedule.map(): RecurrentInfoSchedule =
    RecurrentInfoSchedule(
        date = date,
        amount = amount
    )