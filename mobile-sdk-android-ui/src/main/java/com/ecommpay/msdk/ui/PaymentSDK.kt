package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment


class PaymentSDK(context: Context, paymentOptions: PaymentOptions) {
    val intent =
        PaymentActivity.buildPaymentIntent(context = context, paymentOptions = paymentOptions)

    fun openPaymentScreen(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }

    fun openPaymentScreen(fragment: Fragment, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }
}