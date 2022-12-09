package com.paymentpage.ui.msdk.sample.domain.ui.additionalFields

import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface AdditionalFieldsViewIntents: ViewIntents {
    data class ChangeField(val viewData: AdditionalFieldsViewState) : AdditionalFieldsViewIntents
    object Exit: AdditionalFieldsViewIntents
}