package com.ecommpay.msdk.ui.mappers

import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.ecommpay.msdk.ui.EcmpPaymentOptions
import com.ecommpay.msdk.ui.EcmpScreenDisplayMode
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.ui.*

internal fun EcmpPaymentOptions.map(): SDKPaymentOptions =
    SDKPaymentOptions(
        paymentInfo = paymentInfo,
        recurrentInfo = recurrentData,
        recipientInfo = recipientInfo,
        actionType = SDKActionType.valueOf(actionType.name),
        screenDisplayModes = screenDisplayModes.map(),
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

internal fun EcmpScreenDisplayMode.map(): SDKScreenDisplayMode? =
    SDKScreenDisplayMode.values().find { it.name == name }

internal fun List<EcmpScreenDisplayMode>.map(): List<SDKScreenDisplayMode> =
    mapNotNull { it.map() }