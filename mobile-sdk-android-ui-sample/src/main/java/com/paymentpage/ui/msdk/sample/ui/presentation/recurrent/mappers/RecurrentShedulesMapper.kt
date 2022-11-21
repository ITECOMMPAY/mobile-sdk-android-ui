package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.mappers

import com.paymentpage.msdk.core.domain.entities.RecurrentInfoSchedule
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule

internal fun RecurrentDataSchedule.map(): RecurrentInfoSchedule =
    RecurrentInfoSchedule(
        date = date,
        amount = amount
    )