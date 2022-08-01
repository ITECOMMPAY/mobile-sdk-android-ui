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
import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.navigation.NavigationComponent
import com.paymentpage.ui.msdk.sample.utils.SignatureGenerator

class SampleActivity : ComponentActivity() {
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
                            NavigationComponent {
                                startPaymentPage()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startPaymentPage() {
        val paymentData = ProcessRepository.paymentData
        val additionalFieldsToSend = ProcessRepository.additionalFields
        val payment = PaymentInfo(
            forcePaymentMethod = paymentData.forcePaymentMethod,
            projectId = paymentData.projectId ?: -1,
            paymentId = paymentData.paymentId,
            paymentAmount = paymentData.paymentAmount ?: -1,
            paymentCurrency = paymentData.paymentCurrency,
            customerId = paymentData.customerId,
            paymentDescription = paymentData.paymentDescription
        )
        payment.signature =
            SignatureGenerator.generateSignature(
                payment.getParamsForSignature(), paymentData.secretKey
            )
        val paymentOptions = paymentOptions {
            logoImage = paymentData.bitmap
            brandColor = paymentData.brandColor
            paymentInfo = payment
            merchantId = paymentData.merchantId
            merchantName = paymentData.merchantName
            additionalFields = additionalFieldsToSend?.toMutableList() ?: mutableListOf()
        }
        val sdk = PaymentSDK(context = this.applicationContext, paymentOptions = paymentOptions)

        val intent = sdk.intent
        intent.putExtra(Constants.EXTRA_API_HOST, paymentData.apiHost)
        intent.putExtra(
            Constants.EXTRA_WS_API_HOST,
            paymentData.wsApiHost
        )
        if (paymentData.mockModeEnabled)
            intent.putExtra(Constants.EXTRA_MOCK_MODE_ENABLED, paymentData.mockModeEnabled)

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