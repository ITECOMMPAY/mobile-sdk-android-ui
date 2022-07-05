@file:Suppress("unused")

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

    @Suppress("DEPRECATION")
    fun openPaymentScreen(fragment: Fragment, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }

    companion object {

        const val RESULT_SUCCESS = 0 //when payment completed successfully
        const val RESULT_DECLINE = 100 //when payment declined
        const val RESULT_CANCELLED = 200 //when user canceled payment flow

        const val RESULT_FAILED = 300 //when payment completed with fail
        const val EXTRA_PAYMENT_STATUS = "payment_status"

        const val RESULT_ERROR = 500 //when error occurred
        const val EXTRA_ERROR_CODE = "error_code"
        const val EXTRA_ERROR_MESSAGE = "error_message"
    }
}