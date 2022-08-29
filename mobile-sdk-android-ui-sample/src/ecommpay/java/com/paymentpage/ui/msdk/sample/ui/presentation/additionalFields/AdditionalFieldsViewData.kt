package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState

data class AdditionalFieldsViewData(
    val additionalFields: List<EcmpAdditionalField>?,
) : ViewState {
    companion object {
        val defaultViewData = AdditionalFieldsViewData(
            additionalFields = EcmpAdditionalFieldType.values().map { EcmpAdditionalField(type = it) }
        )
    }
}


