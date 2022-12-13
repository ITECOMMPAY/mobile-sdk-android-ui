package com.paymentpage.ui.msdk.sample.domain.ui.threeDSecure

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.data.entities.CommonJson
import com.paymentpage.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI
import com.paymentpage.ui.msdk.sample.domain.ui.base.back
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ThreeDSecureViewUC : BaseViewUC<ThreeDSecureInfoViewIntents, ThreeDSecureViewState>(
    ThreeDSecureViewState()
) {
    private val format = Json { prettyPrint = true }

    init {
        updateState(newState = ThreeDSecureViewState(
            jsonThreeDSecureInfo = ProcessRepository.jsonThreeDSecureInfo ?: format.encodeToString(
                CommonJson.default),
            isEnabledThreeDSecure = ProcessRepository.isEnabledThreeDSecure
        ))
    }

    override suspend fun reduce(viewIntent: ThreeDSecureInfoViewIntents) {
        when (viewIntent) {
            is ThreeDSecureInfoViewIntents.ChangeField -> {
                ProcessRepository.jsonThreeDSecureInfo = viewIntent.json
                updateState(viewState.value.copy(jsonThreeDSecureInfo = viewIntent.json))
            }
            is ThreeDSecureInfoViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledThreeDSecure)
                ProcessRepository.isEnabledThreeDSecure = newValue
                updateState(viewState.value.copy(isEnabledThreeDSecure = newValue))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerAccountInfo -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerAccountInfo = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerShipping -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerShipping = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerMpiResult -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerMpiResult = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemovePaymentMerchantRisk -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.paymentMerchantRisk = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.ResetData -> {
                ProcessRepository.isEnabledThreeDSecure = false
                ProcessRepository.jsonThreeDSecureInfo = format.encodeToString(CommonJson.default)
                updateState(
                    ThreeDSecureViewState(
                    jsonThreeDSecureInfo = format.encodeToString(CommonJson.default),
                    isEnabledThreeDSecure = false
                )
                )
            }
            is ThreeDSecureInfoViewIntents.Exit -> {
                try {
                    val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                    ProcessRepository.commonJson = if (ProcessRepository.isEnabledThreeDSecure) threeDSecureInfo else null
                    back()
                } catch (e: Exception) {
                    pushExternalIntent(SampleViewIntents.ShowMessage(MessageUI.Toast(message = "Json contains mistakes")))
                }
            }
        }
    }
}