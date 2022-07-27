package com.paymentpage.msdk.ui.googlePay

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment

internal class GooglePayActivityContract :
    ActivityResultContract<GooglePayActivityContract.Config, GooglePayActivityContract.Result>() {

    override fun createIntent(context: Context, input: Config): Intent {
        return Intent(context, GooglePayActivity::class.java).apply {
            putExtra(EXTRA_AMOUNT, input.amount)
            putExtra(EXTRA_CURRENCY, input.currency)

            putExtra(EXTRA_ENVIRONMENT, input.merchantEnvironment.name)
            putExtra(EXTRA_MERCHANT_ID, input.merchantId)
            putExtra(EXTRA_MERCHANT_NAME, input.merchantName)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Result {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            val token = intent.getStringExtra(EXTRA_TOKEN)
            Result(token = token)
        } else {
            val errorMessage = intent?.getStringExtra(EXTRA_ERROR_MESSAGE)
            Result(errorMessage = errorMessage)
        }
    }

    companion object {
        const val EXTRA_AMOUNT = "amount"
        const val EXTRA_CURRENCY = "currency"
        const val EXTRA_ENVIRONMENT = "environment"
        const val EXTRA_MERCHANT_ID = "merchant_id"
        const val EXTRA_MERCHANT_NAME = "merchant_name"
        const val EXTRA_TOKEN = "token"
        const val EXTRA_ERROR_MESSAGE = "error_message"
    }

    class Config(
        val merchantId: String,
        val merchantName: String,
        val merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST,
        val amount: Long,
        val currency: String,
    )

    class Result(
        val token: String? = null,
        val errorMessage: String? = null
    )

}