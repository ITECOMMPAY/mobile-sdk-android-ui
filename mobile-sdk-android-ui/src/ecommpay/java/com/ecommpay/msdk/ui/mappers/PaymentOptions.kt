package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.EcmpPaymentOptions
import com.paymentpage.msdk.core.domain.entities.field.FieldType
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.ui.ActionType
import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.SDKPaymentOptions

internal fun EcmpPaymentOptions.map(): SDKPaymentOptions =
    SDKPaymentOptions(
        paymentInfo = paymentInfo,
        recurrentInfo = recurrentData,
        actionType = ActionType.valueOf(actionType.name),

        logoImage = logoImage,
        brandColor = brandColor,

        merchantId = merchantId,
        merchantName = merchantName,
        merchantEnvironment = if (isTestEnvironment) GooglePayEnvironment.TEST else GooglePayEnvironment.PROD,
        additionalFields = additionalFields.map { additionalField ->
            SDKAdditionalField(
                type = additionalField.type?.let { FieldType.valueOf(it.name) }
                    ?: FieldType.UNKNOWN,
                value = additionalField.value
            )
        }
    )