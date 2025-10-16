package com.mglwallet.ui.msdk.sample.domain.mappers

import com.mglwallet.msdk.ui.threeDSecure.EcmpThreeDSecureCustomerInfo
import com.mglwallet.msdk.ui.threeDSecure.EcmpThreeDSecureInfo
import com.mglwallet.ui.msdk.sample.data.storage.entities.threeDSecure.ThreeDSecureInfoModel


internal fun ThreeDSecureInfoModel.map(): EcmpThreeDSecureInfo =
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