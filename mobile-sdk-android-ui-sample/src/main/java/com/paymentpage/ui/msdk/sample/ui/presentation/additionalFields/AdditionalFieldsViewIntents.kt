package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewIntents

sealed interface AdditionalFieldsViewIntents: ViewIntents {
    object Init: AdditionalFieldsViewIntents
    data class ChangeField(val viewData: AdditionalFieldsViewData?) : AdditionalFieldsViewIntents
}