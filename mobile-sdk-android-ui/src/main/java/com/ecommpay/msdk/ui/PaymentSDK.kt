package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context


class PaymentSDK(context: Context, paymentInfo: PaymentInfo) {
    val intent = PaymentActivity.buildPaymentIntent(context = context, paymentInfo = paymentInfo)

    fun openPaymentScreen(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }
}