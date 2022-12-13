package com.paymentpage.ui.msdk.sample.domain.ui.additionalFields

import com.paymentpage.msdk.ui.SDKAdditionalField
import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewState

data class AdditionalFieldsViewState(
    val additionalFields: List<SDKAdditionalField> = emptyList(),
) : ViewState
