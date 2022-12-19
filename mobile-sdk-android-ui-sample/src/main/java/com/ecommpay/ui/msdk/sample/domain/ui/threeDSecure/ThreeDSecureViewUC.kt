package com.ecommpay.ui.msdk.sample.domain.ui.threeDSecure

import com.ecommpay.ui.msdk.sample.data.ProcessRepository
import com.ecommpay.ui.msdk.sample.data.entities.CommonJson
import com.ecommpay.ui.msdk.sample.domain.ui.base.BaseViewUC
import com.ecommpay.ui.msdk.sample.domain.ui.base.MessageUI
import com.ecommpay.ui.msdk.sample.domain.ui.base.back
import com.ecommpay.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ThreeDSecureViewUC : BaseViewUC<ThreeDSecureInfoViewIntents, ThreeDSecureViewState>(
    ThreeDSecureViewState()
) {
    private val format = Json { prettyPrint = true }

    init {
        updateState(newState = ThreeDSecureViewState(
            jsonThreeDSecureInfo = com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo ?: format.encodeToString(
                CommonJson.default),
            isEnabledThreeDSecure = com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledThreeDSecure
        ))
    }

    override suspend fun reduce(viewIntent: ThreeDSecureInfoViewIntents) {
        when (viewIntent) {
            is ThreeDSecureInfoViewIntents.ChangeField -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = viewIntent.json
                updateState(viewState.value.copy(jsonThreeDSecureInfo = viewIntent.json))
            }
            is ThreeDSecureInfoViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value.isEnabledThreeDSecure)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledThreeDSecure = newValue
                updateState(viewState.value.copy(isEnabledThreeDSecure = newValue))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerAccountInfo -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerAccountInfo = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerShipping -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerShipping = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerMpiResult -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.customerMpiResult = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemovePaymentMerchantRisk -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value.jsonThreeDSecureInfo)
                threeDSecureInfo.threeDSecureInfo?.paymentMerchantRisk = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.ResetData -> {
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledThreeDSecure = false
                com.ecommpay.ui.msdk.sample.data.ProcessRepository.jsonThreeDSecureInfo = format.encodeToString(CommonJson.default)
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
                    com.ecommpay.ui.msdk.sample.data.ProcessRepository.commonJson = if (com.ecommpay.ui.msdk.sample.data.ProcessRepository.isEnabledThreeDSecure) threeDSecureInfo else null
                    back()
                } catch (e: Exception) {
                    pushExternalIntent(SampleViewIntents.ShowMessage(MessageUI.Toast(message = "Json contains mistakes")))
                }
            }
        }
    }
}