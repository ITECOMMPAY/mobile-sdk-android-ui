package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure

import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.CommonJson

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