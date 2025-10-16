package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.EcmpRecurrentDataSchedule
import com.mglwallet.ui.msdk.sample.domain.entities.RecurrentDataSchedule

internal fun RecurrentDataSchedule.map(): EcmpRecurrentDataSchedule =
    EcmpRecurrentDataSchedule(
        date = date?.ifEmpty { null },
        amount = amount
    )