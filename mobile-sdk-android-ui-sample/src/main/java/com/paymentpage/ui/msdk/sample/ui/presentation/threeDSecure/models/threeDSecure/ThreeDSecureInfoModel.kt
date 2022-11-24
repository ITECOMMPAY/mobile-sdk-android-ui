package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure

import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.CustomerAccountInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.CustomerMpiResult
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.customer.CustomerShipping
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.payment.PaymentMerchantRisk
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreeDSecureInfoModel(
    @SerialName("address_match")
    val addressMatch: String? = null,
    @SerialName("home_phone")
    val homePhone: String? = null,
    @SerialName("work_phone")
    val workPhone: String? = null,
    @SerialName("billing_region_code")
    val billingRegionCode: String? = null,
    @SerialName("customer_account_info")
    var customerAccountInfo: CustomerAccountInfo? = null,
    @SerialName("customer_shipping")
    var customerShipping: CustomerShipping? = null,
    @SerialName("customer_mpi_result")
    var customerMpiResult: CustomerMpiResult? = null,
    @SerialName("payment_merchant_risk")
    var paymentMerchantRisk: PaymentMerchantRisk? = null
) {
    companion object {
        val default = ThreeDSecureInfoModel(
            addressMatch = "Y",
            homePhone = "79105211111",
            workPhone = "74955211111",
            billingRegionCode = "CRS",
            customerAccountInfo = CustomerAccountInfo.default,
            customerShipping = CustomerShipping.default,
            customerMpiResult = CustomerMpiResult.default,
            paymentMerchantRisk = PaymentMerchantRisk.default
        )
    }
}