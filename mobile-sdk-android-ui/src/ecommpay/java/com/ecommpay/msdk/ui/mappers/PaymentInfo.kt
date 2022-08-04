package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.EcmpPaymentInfo
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType

internal fun EcmpPaymentInfo.map(): PaymentInfo =
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
        forcePaymentMethod = forcePaymentMethod?.value,
        signature = signature
    )