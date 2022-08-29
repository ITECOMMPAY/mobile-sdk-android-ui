package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureMpiResultInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.MpiResult

internal fun MpiResult.map() : EcmpThreeDSecureMpiResultInfo =
    EcmpThreeDSecureMpiResultInfo(
        acsOperationId = acsOperationId,
        authenticationFlow = authenticationFlow,
        authenticationTimestamp = authenticationTimestamp
    )