package com.paymentpage.ui.msdk.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ecommpay.msdk.ui.PaymentSDK
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.paymentOptions
import com.paymentpage.ui.msdk.sample.ui.App
import com.paymentpage.ui.msdk.sample.utils.SignatureGenerator
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold() {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .verticalScroll(rememberScrollState())
                                .padding(it),
                        ) {
                            App(activity = this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    companion object {

        var projectId = 111781
        var paymentId = UUID.randomUUID().toString()
        var paymentAmount: Long = 123
        var paymentCurrency = "USD"
        var customerId = "12"
        var paymentDescription = "Test payment"


        var secretKey: String = "123"
        var apiHost: String = "pp-sdk-3.westresscode.net"
        var wsApiHost: String = "paymentpage-3.westresscode.net"

        var merchantId: String = "BCR2DN6TZ75OBLTH"
        var merchantName: String = "Example Merchant"

        var mockModeEnabled = false
    }

    fun startPaymentPage() {
        val payment = PaymentInfo(
            projectId = projectId,
            paymentId = paymentId,
            paymentAmount = paymentAmount,
            paymentCurrency = paymentCurrency,
            customerId = customerId,
            paymentDescription = paymentDescription
        )
        payment.signature =
            SignatureGenerator.generateSignature(
                payment.getParamsForSignature(), secretKey
            )
        val paymentOptions = paymentOptions {
            paymentInfo = payment
            merchantId = MainActivity.merchantId
            merchantName = MainActivity.merchantName
            //brandColor = "#fcba03"
        }
        val sdk = PaymentSDK(context = this.applicationContext, paymentOptions = paymentOptions)

        val intent = sdk.intent
        intent.putExtra(Constants.EXTRA_API_HOST, apiHost)
        intent.putExtra(
            Constants.EXTRA_WS_API_HOST,
            wsApiHost
        )
        if (mockModeEnabled)
            intent.putExtra(Constants.EXTRA_MOCK_MODE_ENABLED, mockModeEnabled)

        startActivityForResult(intent, 2405)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            PaymentSDK.RESULT_SUCCESS -> {}
            PaymentSDK.RESULT_CANCELLED -> {}
            PaymentSDK.RESULT_DECLINE -> {}
            PaymentSDK.RESULT_ERROR -> {
                val errorCode = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                val message = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
            }
        }
    }
}