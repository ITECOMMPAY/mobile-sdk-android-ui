package com.mglwallet.ui.msdk.sample.domain.ui.additionalFields

import com.mglwallet.msdk.ui.EcmpAdditionalField
import com.mglwallet.ui.msdk.sample.domain.ui.base.ViewState

data class AdditionalFieldsViewState(
    val additionalFields: List<EcmpAdditionalField> = emptyList(),
) : ViewState
