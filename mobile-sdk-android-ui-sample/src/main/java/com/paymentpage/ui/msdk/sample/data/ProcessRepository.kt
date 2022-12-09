package com.paymentpage.ui.msdk.sample.data

import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.SDKAdditionalFieldType
import com.paymentpage.ui.msdk.sample.data.entities.CommonJson
import com.paymentpage.ui.msdk.sample.domain.entities.PaymentData
import com.paymentpage.ui.msdk.sample.domain.entities.RecurrentData

object ProcessRepository {
    var additionalFields: List<SDKAdditionalField> = SDKAdditionalFieldType.values().map { SDKAdditionalField(type = it) }
    var paymentData: PaymentData = PaymentData()
    var recurrentData: RecurrentData = RecurrentData()
    var isEnabledRecurrent: Boolean = false
    var jsonThreeDSecureInfo: String? = null
    var commonJson: CommonJson? = null
    var isEnabledThreeDSecure: Boolean = false
}