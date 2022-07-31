package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.paymentpage.msdk.core.domain.entities.field.FieldType
import com.paymentpage.msdk.ui.AdditionalField
import com.paymentpage.msdk.ui.AdditionalFields
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState

data class AdditionalFieldsViewData(
    val additionalFields: List<AdditionalField>?,
): ViewState {
    companion object {
        val defaultViewData = AdditionalFieldsViewData(
            additionalFields = FieldType.values().map {
                AdditionalField(
                    type = it
                )
            }
        )
    }
}


