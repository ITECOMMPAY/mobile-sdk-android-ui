package com.ecommpay.msdk.ui.threeDSecure

import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureAccountInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureCustomerInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureMpiResultInfo
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureShippingInfo

class EcmpThreeDSecureCustomerInfo(
    addressMatch: String? = null,
    homePhone: String? = null,
    workPhone: String? = null,
    billingRegionCode: String? = null,
    /**
     * object with information about customer account details on record with the web service [EcmpThreeDSecureAccountInfo]
     */
    ecmpThreeDSecureAccountInfo: EcmpThreeDSecureAccountInfo? = null,
    /**
     * object with shipping details [EcmpThreeDSecureShippingInfo]
     */
    ecmpThreeDSecureShippingInfo: EcmpThreeDSecureShippingInfo? = null,
    /**
     * object with information about previous customer authentication  [EcmpThreeDSecureMpiResultInfo]
     */
    ecmpThreeDSecureMpiResultInfo: EcmpThreeDSecureMpiResultInfo? = null,
) : ThreeDSecureCustomerInfo(
    addressMatch = addressMatch,
    homePhone = homePhone,
    workPhone = workPhone,
    billingRegionCode = billingRegionCode,
    accountInfo = ecmpThreeDSecureAccountInfo,
    shippingInfo = ecmpThreeDSecureShippingInfo,
    mpiResultInfo = ecmpThreeDSecureMpiResultInfo
) {
    companion object {
        fun default() = EcmpThreeDSecureCustomerInfo()
    }
}

class EcmpThreeDSecureAccountInfo(
    additional: String? = null,
    ageIndicator: String? = null,
    date: String? = null,
    changeIndicator: String? = null,
    changeDate: String? = null,
    passChangeIndicator: String? = null,
    passChangeDate: String? = null,
    purchaseNumber: Int? = null,
    provisionAttempts: Int? = null,
    activityDay: Int? = null,
    activityYear: Int? = null,
    paymentAgeIndicator: String? = null,
    paymentAge: String? = null,
    suspiciousActivity: String? = null,
    authMethod: String? = null,
    authTime: String? = null,
    authData: String? = null,
) : ThreeDSecureAccountInfo(
    additional = additional,
    ageIndicator = ageIndicator,
    date = date,
    changeIndicator = changeIndicator,
    changeDate = changeDate,
    passChangeIndicator = passChangeIndicator,
    passChangeDate = passChangeDate,
    purchaseNumber = purchaseNumber,
    provisionAttempts = provisionAttempts,
    activityDay = activityDay,
    activityYear = activityYear,
    paymentAgeIndicator = paymentAgeIndicator,
    paymentAge = paymentAge,
    suspiciousActivity = suspiciousActivity,
    authMethod = authMethod,
    authTime = authTime,
    authData = authData
) {
    companion object {
        fun default() = EcmpThreeDSecureAccountInfo()
    }
}

class EcmpThreeDSecureShippingInfo(
    type: String? = null,
    deliveryTime: String? = null,
    deliveryEmail: String? = null,
    addressUsageIndicator: String? = null,
    addressUsage: String? = null,
    city: String? = null,
    country: String? = null,
    address: String? = null,
    postal: String? = null,
    regionCode: String? = null,
    nameIndicator: String? = null,
) : ThreeDSecureShippingInfo(
    type = type,
    deliveryTime = deliveryTime,
    deliveryEmail = deliveryEmail,
    addressUsageIndicator = addressUsageIndicator,
    addressUsage = addressUsage,
    city = city,
    country = country,
    address = address,
    postal = postal,
    regionCode = regionCode,
    nameIndicator = nameIndicator
) {
    companion object {
        fun default() = EcmpThreeDSecureShippingInfo()
    }
}

class EcmpThreeDSecureMpiResultInfo(
    acsOperationId: String? = null,
    authenticationFlow: String? = null,
    authenticationTimestamp: String? = null,
) : ThreeDSecureMpiResultInfo(
    acsOperationId = acsOperationId,
    authenticationFlow = authenticationFlow,
    authenticationTimestamp = authenticationTimestamp
) {
    companion object {
        fun default() = EcmpThreeDSecureMpiResultInfo()
    }
}