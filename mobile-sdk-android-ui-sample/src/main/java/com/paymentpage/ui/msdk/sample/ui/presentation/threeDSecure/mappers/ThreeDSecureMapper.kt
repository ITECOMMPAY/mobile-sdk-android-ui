package com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureCustomerInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.models.threeDSecure.ThreeDSecureInfoModel


internal fun ThreeDSecureInfoModel.map(): ThreeDSecureInfo =
    ThreeDSecureInfo(
        threeDSecureCustomerInfo = ThreeDSecureCustomerInfo(
            addressMatch = addressMatch,
            homePhone = homePhone,
            workPhone = workPhone,
            billingRegionCode = billingRegionCode,
            accountInfo = customerAccountInfo?.customer?.account?.map(),
            shippingInfo = customerShipping?.customer?.shipping?.map(),
            mpiResultInfo = customerMpiResult?.customer?.mpiResult?.map()
        ),
        threeDSecurePaymentInfo = paymentMerchantRisk?.payment?.map()
    )