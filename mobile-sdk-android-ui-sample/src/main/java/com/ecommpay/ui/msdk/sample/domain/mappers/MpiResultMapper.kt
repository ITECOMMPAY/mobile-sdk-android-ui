package com.ecommpay.ui.msdk.sample.domain.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureMpiResultInfo
import com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.customer.MpiResult

internal fun MpiResult.map(): ThreeDSecureMpiResultInfo =
    ThreeDSecureMpiResultInfo(
        acsOperationId = acsOperationId,
        authenticationFlow = authenticationFlow,
        authenticationTimestamp = authenticationTimestamp
    )