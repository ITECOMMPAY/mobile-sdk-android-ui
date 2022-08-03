package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.PaymentData
import com.paymentpage.msdk.core.domain.entities.PaymentInfo

internal fun PaymentData.map(): PaymentInfo =
    PaymentInfo(
        projectId = projectId,
        paymentId = paymentId,
        paymentAmount = paymentAmount,
        paymentCurrency = paymentCurrency,
        paymentDescription = paymentDescription,
        customerId = customerId,
        regionCode = regionCode,
        token = token,
        languageCode = languageCode,
        receiptData = receiptData,
        hideSavedWallets = hideSavedWallets,
        signature = signature
    )