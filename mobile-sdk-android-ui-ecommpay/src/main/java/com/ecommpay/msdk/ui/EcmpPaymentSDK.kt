@file:Suppress("unused")

package com.ecommpay.msdk.ui

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.ecommpay.msdk.ui.mappers.map
import com.paymentpage.msdk.ui.PaymentSDK


class EcmpPaymentSDK(
    context: Context,
    paymentOptions: EcmpPaymentOptions,
    mockModeType: EcmpMockModeType = EcmpMockModeType.DISABLED
) {

    private val paymentSDK = PaymentSDK(
        context = context,
        paymentOptions = paymentOptions.map().also {
            it.footerImage = context.getBitmapFromVectorDrawable(R.drawable.sdk_logo)
            it.footerLabel = context.getString(R.string.powered_by_label)
        },
        mockModeType = mockModeType.map()
    )

    val intent = paymentSDK.intent

    fun openPaymentScreen(activity: Activity, requestCode: Int) {
        paymentSDK.openPaymentScreen(activity = activity, requestCode = requestCode)
    }

    @Suppress("DEPRECATION")
    fun openPaymentScreen(fragment: Fragment, requestCode: Int) {
        paymentSDK.openPaymentScreen(fragment = fragment, requestCode = requestCode)
    }

    companion object {

        const val RESULT_SUCCESS = PaymentSDK.RESULT_SUCCESS
        const val RESULT_DECLINE = PaymentSDK.RESULT_DECLINE
        const val RESULT_CANCELLED = PaymentSDK.RESULT_CANCELLED

        const val EXTRA_PAYMENT = PaymentSDK.EXTRA_PAYMENT
        const val RESULT_ERROR = PaymentSDK.RESULT_ERROR
        const val EXTRA_ERROR_CODE = PaymentSDK.EXTRA_ERROR_CODE
        const val EXTRA_ERROR_MESSAGE = PaymentSDK.EXTRA_ERROR_MESSAGE

        const val EXTRA_API_HOST = PaymentSDK.EXTRA_API_HOST
        const val EXTRA_WS_API_HOST = PaymentSDK.EXTRA_WS_API_HOST
    }

    enum class EcmpMockModeType {
        DISABLED, SUCCESS, DECLINE
    }
}