package com.paymentpage.ui.msdk.sample.data

import com.ecommpay.msdk.ui.AdditionalField
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldsViewData
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData

object ProcessRepository {
    var additionalFields: List<AdditionalField>? = AdditionalFieldsViewData.defaultViewData.additionalFields
    var paymentData: PaymentData = PaymentData.defaultPaymentData
}