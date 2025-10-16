package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.threeDSecure.EcmpThreeDSecureMpiResultInfo
import com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.customer.MpiResult

internal fun MpiResult.map(): EcmpThreeDSecureMpiResultInfo =
    EcmpThreeDSecureMpiResultInfo(
        acsOperationId = acsOperationId,
        authenticationFlow = authenticationFlow,
        authenticationTimestamp = authenticationTimestamp
    )