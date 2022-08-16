package com.paymentpage.ui.msdk.sample.data

import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.msdk.ui.EcmpRecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields.AdditionalFieldsViewData
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.RecurrentViewData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentData
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models.RecurrentDataSchedule

object ProcessRepository {
    var additionalFields: List<EcmpAdditionalField>? = AdditionalFieldsViewData.defaultViewData.additionalFields
    var paymentData: PaymentData = PaymentData.defaultPaymentData
    var recurrentData: RecurrentData? = RecurrentData.defaultData
    var recurrentDataSchedules: List<RecurrentDataSchedule>? = null
}