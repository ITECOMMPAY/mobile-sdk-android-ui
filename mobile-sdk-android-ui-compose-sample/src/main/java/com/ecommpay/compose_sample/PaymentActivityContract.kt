package com.ecommpay.compose_sample

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.ui.main.PaymentActivity

class PaymentActivityContract : ActivityResultContract<PaymentInfo, Int?>() {

    override fun createIntent(context: Context, input: PaymentInfo): Intent {
        return PaymentActivity.buildPaymentIntent(context = context, paymentInfo = input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? =
        when (resultCode) {
            0 -> 0
            else -> intent?.getIntExtra("my_result_key", 42)
        }
}