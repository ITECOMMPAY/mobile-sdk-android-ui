package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.ecommpay.msdk.ui.EcmpPaymentOptions
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.SDKAdditionalFieldType
import com.paymentpage.msdk.ui.SDKPaymentOptions

internal fun EcmpPaymentOptions.map(): SDKPaymentOptions =
    SDKPaymentOptions(
        paymentInfo = paymentInfo,
        recurrentInfo = recurrentData,
        recipientInfo = recipientInfo,
        actionType = SDKActionType.valueOf(actionType.name),

        logoImage = logoImage,
        brandColor = brandColor,

        merchantId = merchantId,
        merchantName = merchantName,
        merchantEnvironment = if (isTestEnvironment) GooglePayEnvironment.TEST else GooglePayEnvironment.PROD,
        additionalFields = additionalFields.map { additionalField ->
            SDKAdditionalField(
                type = additionalField.type?.map(),
                value = additionalField.value
            )
        }
    )


internal fun EcmpAdditionalFieldType.map(): SDKAdditionalFieldType? =
    SDKAdditionalFieldType.values().find { it.value == value }