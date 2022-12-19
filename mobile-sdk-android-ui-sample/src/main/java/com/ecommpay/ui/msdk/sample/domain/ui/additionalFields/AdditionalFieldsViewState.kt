package com.ecommpay.ui.msdk.sample.domain.ui.additionalFields

import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewState

data class AdditionalFieldsViewState(
    val additionalFields: List<EcmpAdditionalField> = emptyList(),
) : ViewState
