package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.presentation.base.BaseViewUseCase

class RecurrentViewUC: BaseViewUseCase<RecurrentViewIntents, RecurrentViewData>() {
    override suspend fun reduce(viewIntent: RecurrentViewIntents) {
        when(viewIntent) {
            is RecurrentViewIntents.Init -> updateState(RecurrentViewData(ProcessRepository.recurrentData, ProcessRepository.recurrentDataSchedules))
            is RecurrentViewIntents.ChangeField -> {
                ProcessRepository.recurrentData = viewIntent.viewData?.recurrentData
                ProcessRepository.recurrentDataSchedules = viewIntent.viewData?.schedules
                updateState(viewIntent.viewData)
            }
        }
    }
}