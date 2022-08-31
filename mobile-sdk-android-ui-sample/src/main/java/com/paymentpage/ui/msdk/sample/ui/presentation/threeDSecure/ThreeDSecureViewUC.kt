package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure

import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRoutes
import com.paymentpage.ui.msdk.sample.ui.presentation.base.BaseViewUseCase
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewActions
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.CommonJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ThreeDSecureViewUC : BaseViewUseCase<ThreeDSecureInfoViewIntents, ThreeDSecureViewState>() {
    private val format = Json { prettyPrint = true }
    override suspend fun init() {
        updateState(ThreeDSecureViewState(
            jsonThreeDSecureInfo = ProcessRepository.jsonThreeDSecureInfo ?: format.encodeToString(CommonJson.default),
            isEnabledThreeDSecure = ProcessRepository.isEnabledThreeDSecure
        ))
    }

    override suspend fun reduce(viewIntent: ThreeDSecureInfoViewIntents) {
        when (viewIntent) {
            is ThreeDSecureInfoViewIntents.ChangeField -> {
                ProcessRepository.jsonThreeDSecureInfo = viewIntent.json
                updateState(viewState.value?.copy(jsonThreeDSecureInfo = viewIntent.json))
            }
            is ThreeDSecureInfoViewIntents.ChangeCheckbox -> {
                val newValue = !(viewState.value?.isEnabledThreeDSecure ?: false)
                ProcessRepository.isEnabledThreeDSecure = newValue
                updateState(viewState.value?.copy(isEnabledThreeDSecure = newValue))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerAccountInfo -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value?.jsonThreeDSecureInfo ?: "")
                threeDSecureInfo.threeDSecureInfo?.customerAccountInfo = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value?.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerShipping -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value?.jsonThreeDSecureInfo ?: "")
                threeDSecureInfo.threeDSecureInfo?.customerShipping = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value?.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemoveCustomerMpiResult -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value?.jsonThreeDSecureInfo ?: "")
                threeDSecureInfo.threeDSecureInfo?.customerMpiResult = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value?.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.RemovePaymentMerchantRisk -> {
                val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value?.jsonThreeDSecureInfo ?: "")
                threeDSecureInfo.threeDSecureInfo?.paymentMerchantRisk = null
                val newJsonThreeDSecureInfo = format.encodeToString(threeDSecureInfo)
                ProcessRepository.jsonThreeDSecureInfo = newJsonThreeDSecureInfo
                updateState(viewState.value?.copy(jsonThreeDSecureInfo = newJsonThreeDSecureInfo))
            }
            is ThreeDSecureInfoViewIntents.ResetData -> {
                ProcessRepository.isEnabledThreeDSecure = false
                ProcessRepository.jsonThreeDSecureInfo = format.encodeToString(CommonJson.default)
                updateState(ThreeDSecureViewState(
                    jsonThreeDSecureInfo = format.encodeToString(CommonJson.default),
                    isEnabledThreeDSecure = false
                ))
            }
            is ThreeDSecureInfoViewIntents.Exit -> {
                try {
                    val threeDSecureInfo = Json.decodeFromString<CommonJson>(viewState.value?.jsonThreeDSecureInfo ?: "")
                    ProcessRepository.commonJson = if (ProcessRepository.isEnabledThreeDSecure) threeDSecureInfo else null
                    launchAction(NavRoutes.Back)
                } catch (e: Exception) {
                    launchAction(MainViewActions.ShowToast(message = "Json contains mistakes"))
                }
            }
        }
    }
}