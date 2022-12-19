package com.ecommpay.ui.msdk.sample.domain.entities

data class RecurrentData(
    val register: Boolean? = null,
    val type: String? = null,
    val expiryDay: String? = null,
    val expiryMonth: String? = null,
    val expiryYear: String? = null,
    val period: String? = null,
    val time: String? = null,
    val startDate: String? = null,
    val scheduledPaymentID: String? = null,
    val amount: Long? = null,
    val schedule: List<RecurrentDataSchedule>? = null
)
