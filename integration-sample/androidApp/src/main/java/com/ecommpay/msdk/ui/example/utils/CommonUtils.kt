package com.ecommpay.msdk.ui.example.utils

import java.util.*

object CommonUtils {
    fun getRandomPaymentId(): String {
        val randomNumber = Random().nextInt(9999) + 1000
        return "example_payment_id_$randomNumber"
    }
}