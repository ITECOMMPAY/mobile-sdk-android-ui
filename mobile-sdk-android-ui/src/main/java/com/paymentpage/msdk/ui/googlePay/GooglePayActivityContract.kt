package com.paymentpage.msdk.ui.googlePay

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.wallet.PaymentData
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import org.json.JSONObject

 class GooglePayActivityContract :
    ActivityResultContract<GooglePayActivityContract.Config, String?>() {

    override fun createIntent(context: Context, input: Config): Intent {
        return Intent(context, GooglePayActivity::class.java).apply {
            putExtra(AMOUNT_EXTRA, input.amount)
            putExtra(CURRENCY_EXTRA, input.currency)

            putExtra(ENV_EXTRA, input.merchantEnvironment.name)
            putExtra(MERCHANT_ID_EXTRA, input.merchantId)
            putExtra(MERCHANT_NAME_EXTRA, input.merchantName)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (intent == null) return null
        return if (resultCode == Activity.RESULT_OK) {
            val paymentData = PaymentData.getFromIntent(intent)
            val paymentInformation = paymentData?.toJson() ?: return null
            val paymentMethodData: JSONObject =
                JSONObject(paymentInformation).getJSONObject("paymentMethodData")
            paymentMethodData.getJSONObject("tokenizationData").getString("token")
        } else null
    }

    companion object {
        const val AMOUNT_EXTRA = "amount"
        const val CURRENCY_EXTRA = "currency"
        const val ENV_EXTRA = "env"
        const val MERCHANT_ID_EXTRA = "merchant_id"
        const val MERCHANT_NAME_EXTRA = "merchant_name"
    }

    class Config(
        val merchantId: String,
        val merchantName: String,
        val merchantEnvironment: GooglePayEnvironment = GooglePayEnvironment.TEST,
        val amount: Long,
        val currency: String,
    )
}