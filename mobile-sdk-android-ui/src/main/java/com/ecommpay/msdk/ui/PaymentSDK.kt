package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context


class PaymentSDK(private val context: Context, private val paymentInfo: PaymentInfo) {
    val intent = PaymentActivity.buildPaymentIntent(context = context, paymentInfo = paymentInfo)

    @JvmOverloads
    fun openPaymentScreen(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }
}