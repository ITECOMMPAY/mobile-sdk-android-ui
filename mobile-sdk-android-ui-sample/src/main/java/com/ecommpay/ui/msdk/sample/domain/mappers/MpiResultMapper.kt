package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureMpiResultInfo
import com.ecommpay.ui.msdk.sample.data.storage.entities.threeDSecure.customer.MpiResult

internal fun MpiResult.map(): EcmpThreeDSecureMpiResultInfo =
    EcmpThreeDSecureMpiResultInfo(
        acsOperationId = acsOperationId,
        authenticationFlow = authenticationFlow,
        authenticationTimestamp = authenticationTimestamp
    )