package com.ecommpay.ui.msdk.sample.domain.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureAccountInfo
import com.ecommpay.ui.msdk.sample.data.storage.entities.threeDSecure.customer.Account

internal fun Account.map(): EcmpThreeDSecureAccountInfo =
    EcmpThreeDSecureAccountInfo(
        additional = additional,
        ageIndicator = ageIndicator,
        date = date,
        changeIndicator = changeIndicator,
        changeDate = changeDate,
        passChangeIndicator = passChangeIndicator,
        passChangeDate = passChangeDate,
        purchaseNumber = purchaseNumber,
        provisionAttempts = provisionAttempts,
        activityDay = activityDay,
        activityYear = activityYear,
        paymentAgeIndicator = paymentAgeIndicator,
        paymentAge = paymentAge,
        suspiciousActivity = suspiciousActivity,
        authMethod = authMethod,
        authTime = authTime,
        authData = authData
    )