package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureMpiResultInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.MpiResult

internal fun MpiResult.map(): ThreeDSecureMpiResultInfo =
    ThreeDSecureMpiResultInfo(
        acsOperationId = acsOperationId,
        authenticationFlow = authenticationFlow,
        authenticationTimestamp = authenticationTimestamp
    )