package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureCustomerInfo
import com.ecommpay.msdk.ui.threeDSecure.EcmpThreeDSecureInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.ThreeDSecureInfo

internal fun ThreeDSecureInfo.map() : EcmpThreeDSecureInfo =
    EcmpThreeDSecureInfo(
        ecmpThreeDSecureCustomerInfo = EcmpThreeDSecureCustomerInfo(
            addressMatch = addressMatch,
            homePhone = homePhone,
            workPhone = workPhone,
            billingRegionCode = billingRegionCode,
            ecmpThreeDSecureAccountInfo = customerAccountInfo?.customer?.account?.map(),
            ecmpThreeDSecureShippingInfo = customerShipping?.customer?.shipping?.map(),
            ecmpThreeDSecureMpiResultInfo = customerMpiResult?.customer?.mpiResult?.map()
        ),
        ecmpThreeDSecurePaymentInfo = paymentMerchantRisk?.payment?.map()
    )