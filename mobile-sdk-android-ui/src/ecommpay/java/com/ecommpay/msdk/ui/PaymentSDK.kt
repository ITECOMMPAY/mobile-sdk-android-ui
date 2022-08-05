@file:Suppress("unused")

package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.ecommpay.msdk.ui.mappers.map
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.base.Constants


class PaymentSDK(context: Context, paymentOptions: EcmpPaymentOptions) {
    val intent =
        PaymentActivity.buildPaymentIntent(
            context = context,
            paymentOptions = paymentOptions.map()
        )

    fun openPaymentScreen(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }

    @Suppress("DEPRECATION")
    fun openPaymentScreen(fragment: Fragment, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }

    companion object {

        const val RESULT_SUCCESS = Constants.RESULT_SUCCESS
        const val RESULT_DECLINE = Constants.RESULT_DECLINE
        const val RESULT_CANCELLED = Constants.RESULT_CANCELLED

        const val RESULT_ERROR = Constants.RESULT_ERROR
        const val EXTRA_ERROR_CODE = Constants.EXTRA_ERROR_CODE
        const val EXTRA_ERROR_MESSAGE = Constants.EXTRA_ERROR_MESSAGE
    }
}