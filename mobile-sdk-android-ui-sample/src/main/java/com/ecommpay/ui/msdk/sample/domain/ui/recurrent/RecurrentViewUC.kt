package com.ecommpay.ui.msdk.sample.domain.ui.recurrent

import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentDataSchedule
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.base.back
import java.util.*

class RecurrentViewUC : BaseViewUC<RecurrentViewIntents, RecurrentViewState>(RecurrentViewState()) {

    init {
        updateState(
            newState = RecurrentViewState(
                recurrentData = ProcessRepository.recurrentData ?: RecurrentData(),
                isEnabledRecurrent = ProcessRepository.isEnabledRecurrent
            )
        )
    }

    override suspend fun reduce(viewIntent: RecurrentViewIntents) {
        when (viewIntent) {
            is RecurrentViewIntents.ChangeField -> {
                ProcessRepository.recurrentData = viewIntent.recurrentData
                updateState(viewState.value.copy(recurrentData = viewIntent.recurrentData))
            }
            is RecurrentViewIntents.Exit -> {
                ProcessRepository.recurrentData =
                    if (
                        ProcessRepository.isEnabledRecurrent ||
                        ProcessRepository.recurrentData?.register == false
                    )
                        viewState.value.recurrentData
                    else
                        null
                back()
            }
            is RecurrentViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledRecurrent)
                ProcessRepository.isEnabledRecurrent = newValue
                ProcessRepository.recurrentData = viewState.value.recurrentData.copy(register = newValue)
                updateState(
                    viewState.value.copy(
                        isEnabledRecurrent = newValue,
                        recurrentData = viewState.value.recurrentData.copy(register = newValue)
                    )
                )
            }
            is RecurrentViewIntents.ResetData -> {
                ProcessRepository.isEnabledRecurrent = false
                ProcessRepository.recurrentData = RecurrentData()
                updateState(
                    RecurrentViewState(
                        recurrentData = RecurrentData().copy(register = false),
                        isEnabledRecurrent =  false
                    )
                )
            }
            is RecurrentViewIntents.FillMockData -> {
                val randomExpiryYear = "20${(24..30).random()}"
                val randomStartYear = (randomExpiryYear.toInt() + 1).toString()
                val mockSchedule = RecurrentDataSchedule().copy(
                    date = "10-08-202${(0..9).random()}",
                    amount = (1000..2000).random().toLong()
                )
                ProcessRepository.isEnabledRecurrent = true
                ProcessRepository.recurrentData = RecurrentData.mockData.copy(
                    schedule = viewState.value.recurrentData.schedule?.map {
                        mockSchedule
                    } ?: listOf(
                        mockSchedule
                    )
                )
                updateState(
                    RecurrentViewState(
                        recurrentData = RecurrentData.mockData.copy(
                            schedule = viewState.value.recurrentData.schedule?.map {
                                mockSchedule
                            } ?: listOf(
                                mockSchedule
                            ),
                            scheduledPaymentID = "sdk_recurrent_${
                                UUID.randomUUID().toString().take(8)
                            }",
                            expiryMonth = "0${(1..9).random()}",
                            expiryYear = randomExpiryYear,
                            startDate = "10-08-$randomStartYear",
                            amount = (1L..1000L).random(),
                        ),
                        isEnabledRecurrent = true
                    )
                )
            }
        }
    }
}