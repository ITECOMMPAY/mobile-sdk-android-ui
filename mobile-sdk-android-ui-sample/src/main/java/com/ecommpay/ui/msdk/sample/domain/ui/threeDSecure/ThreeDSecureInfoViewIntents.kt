package com.ecommpay.ui.msdk.sample.domain.ui.threeDSecure

import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface ThreeDSecureInfoViewIntents : ViewIntents {
    object ChangeCheckbox : ThreeDSecureInfoViewIntents
    data class ChangeField(val json: String) : ThreeDSecureInfoViewIntents
    object ResetData : ThreeDSecureInfoViewIntents
    object Exit : ThreeDSecureInfoViewIntents
    object RemoveCustomerAccountInfo : ThreeDSecureInfoViewIntents
    object RemoveCustomerShipping : ThreeDSecureInfoViewIntents
    object RemoveCustomerMpiResult : ThreeDSecureInfoViewIntents
    object RemovePaymentMerchantRisk : ThreeDSecureInfoViewIntents
}