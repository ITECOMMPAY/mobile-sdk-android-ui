package com.ecommpay.ui.msdk.sample.data

import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.ecommpay.ui.msdk.sample.data.entities.CommonJson
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData

object ProcessRepository {
    var additionalFields: List<EcmpAdditionalField> =
        EcmpAdditionalFieldType.values().map { EcmpAdditionalField(type = it) }
    var paymentData: PaymentData = PaymentData()
    var recurrentData: RecurrentData = RecurrentData()
    var isEnabledRecurrent: Boolean = false
    var jsonThreeDSecureInfo: String? = null
    var commonJson: CommonJson? = null
    var isEnabledThreeDSecure: Boolean = false
}