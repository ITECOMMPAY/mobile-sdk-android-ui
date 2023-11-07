@file:Suppress("unused")

package com.paymentpage.msdk.ui

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.paymentpage.msdk.ui.base.Constants


class PaymentSDK(
    context: Context,
    paymentOptions: SDKPaymentOptions,
    mockModeType: SDKMockModeType = SDKMockModeType.DISABLED
) {

    val intent = PaymentActivity.buildPaymentIntent(
        context = context,
        paymentOptions = paymentOptions,
        mockModeType = mockModeType
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

        const val EXTRA_PAYMENT = Constants.EXTRA_PAYMENT
        const val RESULT_ERROR = Constants.RESULT_ERROR
        const val EXTRA_ERROR_CODE = Constants.EXTRA_ERROR_CODE
        const val EXTRA_ERROR_MESSAGE = Constants.EXTRA_ERROR_MESSAGE

        const val EXTRA_API_HOST = Constants.EXTRA_API_HOST
        const val EXTRA_WS_API_HOST = Constants.EXTRA_WS_API_HOST
    }
}