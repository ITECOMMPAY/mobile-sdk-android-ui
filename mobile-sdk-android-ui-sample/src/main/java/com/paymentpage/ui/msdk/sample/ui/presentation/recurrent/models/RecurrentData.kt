package com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.models

data class RecurrentData(
    val register: Boolean = true,
    val type: String? = null,
    val expiryDay: String? = null,
    val expiryMonth: String? = null,
    val expiryYear: String? = null,
    val period: String? = null,
    val time: String? = null,
    val startTime: String? = null,
    val scheduledPaymentID: String? = null,
    val amount: Long? = null,
) {
    companion object {
        val defaultData = RecurrentData()
    }
}
