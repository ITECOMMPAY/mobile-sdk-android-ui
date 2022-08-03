package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.ecommpay.msdk.ui.AdditionalField
import com.ecommpay.msdk.ui.AdditionalFieldType
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState

data class AdditionalFieldsViewData(
    val additionalFields: List<AdditionalField>?,
) : ViewState {
    companion object {
        val defaultViewData = AdditionalFieldsViewData(
            additionalFields = AdditionalFieldType.values().map { AdditionalField(type = it) }
        )
    }
}


