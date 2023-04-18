package com.ecommpay.ui.msdk.sample.domain.entities

import java.util.*

data class RecurrentData(
    val register: Boolean = true,
    val type: String? = null,
    val expiryDay: String? = null,
    val expiryMonth: String? = null,
    val expiryYear: String? = null,
    val period: String? = null,
    val interval: Int? = null,
    val time: String? = null,
    val startDate: String? = null,
    val scheduledPaymentID: String? = null,
    val amount: Long? = null,
    val schedule: List<RecurrentDataSchedule>? = null,
) {
    companion object {
        private val randomExpiryYear = "20${(24..30).random()}"
        private val randomStartYear = (randomExpiryYear.toInt() + 1).toString()
        val mockData = RecurrentData(
            register = true,
            type = "R",
            expiryDay = "06",
            expiryMonth = "0${(1..9).random()}",
            expiryYear = randomExpiryYear,
            period = "M",
            interval = 1,
            time = "12:00:00",
            startDate = "10-08-$randomStartYear",
            scheduledPaymentID = "sdk_recurrent_${
                UUID.randomUUID().toString().take(8)
            }",
            amount = (1L..1000L).random(),
            schedule = listOf(RecurrentDataSchedule())
        )
    }
}
