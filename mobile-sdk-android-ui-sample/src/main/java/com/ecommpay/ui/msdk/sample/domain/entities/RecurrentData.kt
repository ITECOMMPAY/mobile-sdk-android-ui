package com.ecommpay.ui.msdk.sample.domain.entities

import java.util.*

data class RecurrentData(
    val register: Boolean = true,
    val type: String? = null,
    val expiryDay: String? = null,
    val expiryMonth: String? = null,
    val expiryYear: String? = null,
    val period: String? = null,
    val time: String? = null,
    val startDate: String? = null,
    val scheduledPaymentID: String? = null,
    val amount: Long? = null,
    val schedule: List<RecurrentDataSchedule>? = null,
) {
    companion object {
        val mockData = RecurrentData(
            register = true,
            type = "R",
            expiryDay = "06",
            expiryMonth = "11",
            expiryYear = "2026",
            period = "M",
            time = "12:00:00",
            startDate = "10-08-20${(24..99).random()}",
            scheduledPaymentID = "sdk_recurrent_${
                UUID.randomUUID().toString().take(8)
            }",
            amount = 1000,
            schedule = listOf(RecurrentDataSchedule())
        )
    }
}
