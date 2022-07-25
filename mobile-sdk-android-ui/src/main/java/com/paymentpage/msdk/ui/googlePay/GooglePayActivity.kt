package com.paymentpage.msdk.ui.googlePay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.wallet.*
import com.paymentpage.msdk.core.domain.interactors.pay.googlePay.GooglePayEnvironment
import com.paymentpage.msdk.core.googlePay.GooglePayHelper
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.Constants
import org.json.JSONObject
import java.math.BigDecimal

 class GooglePayActivity : AppCompatActivity() {

    private lateinit var googlePayHelper: GooglePayHelper
    private lateinit var googlePayClient: PaymentsClient

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
            window.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_pay)

        val amount = intent.extras?.getLong(GooglePayActivityContract.AMOUNT_EXTRA)
        val currency = intent.extras?.getString(GooglePayActivityContract.CURRENCY_EXTRA)
        val merchantName = intent.extras?.getString(GooglePayActivityContract.MERCHANT_NAME_EXTRA)
        val merchantId = intent.extras?.getString(GooglePayActivityContract.MERCHANT_ID_EXTRA)
        val envName = intent.extras?.getString(GooglePayActivityContract.ENV_EXTRA)

        if (amount != null && !currency.isNullOrEmpty() && !merchantName.isNullOrEmpty() && !merchantId.isNullOrEmpty() && !envName.isNullOrEmpty()) {

            val env = GooglePayEnvironment.values().firstOrNull { it.name == envName }
                ?: GooglePayEnvironment.TEST
            googlePayHelper = GooglePayHelper(merchantId, merchantName)
            googlePayClient = Wallet.getPaymentsClient(
                this@GooglePayActivity,
                Wallet.WalletOptions.Builder()
                    .setEnvironment(if (env == GooglePayEnvironment.TEST) WalletConstants.ENVIRONMENT_TEST else WalletConstants.ENVIRONMENT_PRODUCTION)//test
                    //.setTheme(WalletConstants.THEME_LIGHT)
                    .build()
            )
            val request =
                IsReadyToPayRequest.fromJson(googlePayHelper.createReadyToPayRequest().toString())
            googlePayClient.isReadyToPay(request).addOnCompleteListener { completedTask ->
                setGooglePayAvailable(completedTask.isSuccessful, amount, currency)
            }


        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun setGooglePayAvailable(available: Boolean, amount: Long, currency: String) {
        if (available) {
            val googleJson =
                googlePayHelper.createPaymentDataRequest(
                    BigDecimal.valueOf(amount / 100.0),
                    currency
                ).toString()
            val gpayRequest = PaymentDataRequest.fromJson(googleJson)

            AutoResolveHelper.resolveTask(
                googlePayClient.loadPaymentData(gpayRequest),
                this@GooglePayActivity,
                Constants.GOOGLE_PAY_ACTIVITY_REQUEST_CODE
            )
        } else {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK ->
                data?.let { intent ->
                    val paymentData = PaymentData.getFromIntent(intent)
                    val paymentInformation = paymentData?.toJson() ?: return
                    val paymentMethodData: JSONObject =
                        JSONObject(paymentInformation).getJSONObject("paymentMethodData")
                    val token =
                        paymentMethodData.getJSONObject("tokenizationData").getString("token")
                }

            RESULT_CANCELED -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            AutoResolveHelper.RESULT_ERROR -> {
                val status = AutoResolveHelper.getStatusFromIntent(data)

                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }


    }
}