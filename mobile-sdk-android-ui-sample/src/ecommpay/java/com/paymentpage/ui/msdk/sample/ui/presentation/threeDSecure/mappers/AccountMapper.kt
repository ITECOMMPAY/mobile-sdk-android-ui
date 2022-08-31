package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureAccountInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.Account

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