package com.ecommpay.ui.msdk.sample.data

import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.ecommpay.ui.msdk.sample.data.entities.CommonJson
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.domain.entities.RecipientData
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData

object ProcessRepository {
    var additionalFields: List<EcmpAdditionalField> =
        EcmpAdditionalFieldType.values().map { EcmpAdditionalField(type = it) }
    var paymentData: PaymentData = PaymentData()
    //Recurrent
    var recurrentData: RecurrentData? = null
    var isEnabledRecurrent: Boolean = false
    //Recipient
    var recipientData: RecipientData? = null
    var isEnabledRecipient: Boolean = false
    //3DS
    var jsonThreeDSecureInfo: String? = null
    var commonJson: CommonJson? = null
    var isEnabledThreeDSecure: Boolean = false
}