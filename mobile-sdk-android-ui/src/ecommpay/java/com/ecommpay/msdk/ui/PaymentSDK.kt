@file:Suppress("unused")

package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.PaymentActivity


class PaymentSDK(context: Context, paymentOptions: PaymentOptions) {
    val intent =
        PaymentActivity.buildPaymentIntent(
            context = context,
            paymentInfo = paymentOptions.paymentInfo
                ?: throw IllegalAccessException("Payment Info can not be null"),
            recurrentInfo = paymentOptions.recurrentInfo,
            threeDSecureInfo = paymentOptions.threeDSecureInfo,
            recipientInfo = paymentOptions.recipientInfo,
            additionalFields = paymentOptions.additionalFields,
            logoImage = paymentOptions.logoImage
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

        const val RESULT_FAILED = Constants.RESULT_FAILED
        const val EXTRA_PAYMENT_STATUS = Constants.EXTRA_PAYMENT_STATUS

        const val RESULT_ERROR = Constants.RESULT_ERROR
        const val EXTRA_ERROR_CODE = Constants.EXTRA_ERROR_CODE
        const val EXTRA_ERROR_MESSAGE = Constants.EXTRA_ERROR_MESSAGE
    }
}