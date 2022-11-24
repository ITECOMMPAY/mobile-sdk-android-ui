package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.msdk.ui.SDKAdditionalFieldType
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState

data class AdditionalFieldsViewData(
    val additionalFields: List<SDKAdditionalField>?,
) : ViewState {
    companion object {
        val defaultViewData = AdditionalFieldsViewData(
            additionalFields = SDKAdditionalFieldType.values().map { SDKAdditionalField(type = it) }
        )
    }
}


