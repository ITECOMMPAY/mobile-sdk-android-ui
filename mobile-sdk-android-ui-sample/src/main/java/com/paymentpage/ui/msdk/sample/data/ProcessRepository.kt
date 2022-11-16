package com.paymentpage.ui.msdk.sample.data

import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldsViewData
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.CommonJson

object ProcessRepository {
    var additionalFields: List<SDKAdditionalField>? =
        AdditionalFieldsViewData.defaultViewData.additionalFields
    var paymentData: PaymentData = PaymentData.defaultPaymentData
    var recurrentData: RecurrentData? = null
    var isEnabledRecurrent: Boolean = false
    var jsonThreeDSecureInfo: String? = null
    var commonJson: CommonJson? = null
    var isEnabledThreeDSecure: Boolean = false
}