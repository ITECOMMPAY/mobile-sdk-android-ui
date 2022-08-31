package com.paymentpage.ui.msdk.sample.ui.presentation.additionalFields

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.presentation.base.BaseViewUseCase

class AdditionalFieldsViewUC: BaseViewUseCase<AdditionalFieldsViewIntents, AdditionalFieldsViewData>() {
    override suspend fun reduce(viewIntent: AdditionalFieldsViewIntents) {
        when (viewIntent) {
            is AdditionalFieldsViewIntents.ChangeField -> {
                ProcessRepository.additionalFields = viewIntent.viewData?.additionalFields
                updateState(viewIntent.viewData)
            }
            is AdditionalFieldsViewIntents.Init -> updateState(AdditionalFieldsViewData(ProcessRepository.additionalFields))
        }
    }
}