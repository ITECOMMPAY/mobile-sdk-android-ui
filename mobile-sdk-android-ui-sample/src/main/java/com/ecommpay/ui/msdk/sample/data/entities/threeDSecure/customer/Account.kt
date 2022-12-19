package com.ecommpay.ui.msdk.sample.data.entities.threeDSecure.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val additional: String? = null,
    @SerialName("age_indicator")
    val ageIndicator: String? = null,
    val date: String? = null,
    @SerialName("change_indicator")
    val changeIndicator: String? = null,
    @SerialName("change_date")
    val changeDate: String? = null,
    @SerialName("pass_change_indicator")
    val passChangeIndicator: String? = null,
    @SerialName("pass_change_date")
    val passChangeDate: String? = null,
    @SerialName("purchase_number")
    val purchaseNumber: Int? = null,
    @SerialName("provision_attempts")
    val provisionAttempts: Int? = null,
    @SerialName("activity_day")
    val activityDay: Int? = null,
    @SerialName("activity_year")
    val activityYear: Int? = null,
    @SerialName("payment_age_indicator")
    val paymentAgeIndicator: String? = null,
    @SerialName("payment_age")
    val paymentAge: String? = null,
    @SerialName("suspicious_activity")
    val suspiciousActivity: String? = null,
    @SerialName("auth_method")
    val authMethod: String? = null,
    @SerialName("auth_time")
    val authTime: String? = null,
    @SerialName("auth_data")
    val authData: String? = null,
) {
    companion object {
        val default = Account(
            additional = "gamer12345",
            ageIndicator = "01",
            date = "01-10-2019",
            changeIndicator = "01",
            changeDate = "01-10-2019",
            passChangeDate = "01-10-2019",
            purchaseNumber = 12,
            provisionAttempts = 16,
            activityDay = 22,
            activityYear = 222,
            passChangeIndicator = "01",
            paymentAgeIndicator = "01",
            paymentAge = "01-10-2019",
            suspiciousActivity = "01",
            authMethod = "01",
            authTime = "01-10-201913:12",
            authData = "login_0102",
        )
    }
}
