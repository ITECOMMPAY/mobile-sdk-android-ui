package com.ecommpay.msdk.ui.models.threeDSecure


/**
 * object with information about the customer
 */
class ThreeDSecureCustomerInfo(
    val addressMatch: String? = null,
    val homePhone: String? = null,
    val workPhone: String? = null,
    val billingRegionCode: String? = null,
    /**
     * object with information about customer account details on record with the web service [ThreeDSecureAccountInfo][com.ecommpay.msdk.core.domain.entities.init.threeDSecure.ThreeDSecureAccountInfo]
     */
    val accountInfo: ThreeDSecureAccountInfo? = null,
    /**
     * object with shipping details [ThreeDSecureShippingInfo][com.ecommpay.msdk.core.domain.entities.init.threeDSecure.ThreeDSecureShippingInfo]
     */
    val shippingInfo: ThreeDSecureShippingInfo? = null,
    /**
     * object with information about previous customer authentication  [ThreeDSecureMpiResultInfo][com.ecommpay.msdk.core.domain.entities.init.threeDSecure.ThreeDSecureMpiResultInfo]
     */
    val mpiResultInfo: ThreeDSecureMpiResultInfo? = null
) {
    companion object {
        fun default() = ThreeDSecureCustomerInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureCustomerInfo(
            addressMatch = addressMatch,
            homePhone = homePhone,
            workPhone = workPhone,
            billingRegionCode = billingRegionCode,
            accountInfo = accountInfo?.map(),
            shippingInfo = shippingInfo?.map(),
            mpiResultInfo = mpiResultInfo?.map()
        )
}

/**
 * object with information about customer account details on record with the web service
 */
class ThreeDSecureAccountInfo(
    val additional: String? = null,
    val ageIndicator: String? = null,
    val date: String? = null,
    val changeIndicator: String? = null,
    val changeDate: String? = null,
    val passChangeIndicator: String? = null,
    val passChangeDate: String? = null,
    val purchaseNumber: Int? = null,
    val provisionAttempts: Int? = null,
    val activityDay: Int? = null,
    val activityYear: Int? = null,
    val paymentAgeIndicator: String? = null,
    val paymentAge: String? = null,
    val suspiciousActivity: String? = null,
    val authMethod: String? = null,
    val authTime: String? = null,
    val authData: String? = null,
) {
    companion object {
        fun default() = ThreeDSecureAccountInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureAccountInfo(
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
            authData = authData,
        )

}


/**
 * object with shipping details
 */
class ThreeDSecureShippingInfo(
    val type: String? = null,
    val deliveryTime: String? = null,
    val deliveryEmail: String? = null,
    val addressUsageIndicator: String? = null,
    val addressUsage: String? = null,
    val city: String? = null,
    val country: String? = null,
    val address: String? = null,
    val postal: String? = null,
    val regionCode: String? = null,
    val nameIndicator: String? = null
) {
    companion object {
        fun default() = ThreeDSecureShippingInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureShippingInfo(
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
            nameIndicator = nameIndicator,
        )
}

/**
 * object with information about previous customer authentication
 */
class ThreeDSecureMpiResultInfo(
    val acsOperationId: String? = null,
    val authenticationFlow: String? = null,
    val authenticationTimestamp: String? = null
) {
    companion object {
        fun default() = ThreeDSecureMpiResultInfo()
    }

    internal fun map() =
        com.ecommpay.msdk.core.domain.entities.threeDSecure.ThreeDSecureMpiResultInfo(
            acsOperationId = acsOperationId,
            authenticationFlow = authenticationFlow,
            authenticationTimestamp = authenticationTimestamp
        )

}