package com.paymentpage.ui.msdk.sample

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
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
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.ui.PaymentSDK
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.SDKPaymentOptions
import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.ui.navigation.NavigationComponent
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewActions
import com.paymentpage.ui.msdk.sample.ui.presentation.recurrent.mappers.map
import com.paymentpage.ui.msdk.sample.ui.presentation.threeDSecure.mappers.map
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
                    Scaffold {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .verticalScroll(rememberScrollState())
                                .padding(it),
                        ) {
                            NavigationComponent { action ->
                                when (action) {
                                    is MainViewActions.Sale -> startPaymentPage()
                                    is MainViewActions.ShowToast -> Toast.makeText(
                                        this@SampleActivity,
                                        action.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startPaymentPage() {
        val repositoryPaymentData = ProcessRepository.paymentData
        val additionalFieldsToSend = ProcessRepository.additionalFields
        val recurrentDataToSend = ProcessRepository.recurrentData
        val threeDSecureInfoToSend = ProcessRepository.commonJson?.threeDSecureInfo
        val paymentInfo = PaymentInfo(
            forcePaymentMethod = repositoryPaymentData.forcePaymentMethod.ifEmpty { null },
            hideSavedWallets = repositoryPaymentData.hideSavedWallets,
            projectId = repositoryPaymentData.projectId ?: -1,
            paymentId = repositoryPaymentData.paymentId,
            paymentAmount = repositoryPaymentData.paymentAmount ?: -1,
            paymentCurrency = repositoryPaymentData.paymentCurrency,
            customerId = repositoryPaymentData.customerId.ifEmpty { null },
            paymentDescription = repositoryPaymentData.paymentDescription.ifEmpty { null },
            languageCode = repositoryPaymentData.languageCode.ifEmpty { null },
            threeDSecureInfo = threeDSecureInfoToSend?.map()
        )
        paymentInfo.signature =
            SignatureGenerator.generateSignature(
                paymentInfo.getParamsForSignature(), repositoryPaymentData.secretKey
            )
        val paymentOptions = SDKPaymentOptions(
            paymentInfo = paymentInfo,
            logoImage = repositoryPaymentData.bitmap,
            brandColor = repositoryPaymentData.brandColor,
            merchantId = repositoryPaymentData.merchantId,
            merchantName = repositoryPaymentData.merchantName,
            recurrentInfo = recurrentDataToSend?.map(),
            additionalFields = additionalFieldsToSend ?: emptyList()
        )

        val sdk = PaymentSDK(
            context = this.applicationContext,
            paymentOptions = paymentOptions,
            mockModeType = repositoryPaymentData.mockModeType,
            apiHost = repositoryPaymentData.apiHost,
            wsApiHost = repositoryPaymentData.wsApiHost
        )
        startActivityForResult(sdk.intent, 2405)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val builder = AlertDialog.Builder(this)
        when (resultCode) {
            PaymentSDK.RESULT_SUCCESS -> {
                builder
                    .setMessage("Your payment is successful")
                    .setPositiveButton(R.string.ok_label) { _: DialogInterface?, _: Int -> }
            }
            PaymentSDK.RESULT_CANCELLED -> {
                builder
                    .setMessage("You cancelled the payment")
                    .setPositiveButton(R.string.ok_label) { _: DialogInterface?, _: Int -> }
            }
            PaymentSDK.RESULT_DECLINE -> {
                builder
                    .setMessage("Your payment was declined")
                    .setPositiveButton(R.string.ok_label) { _: DialogInterface?, _: Int -> }
            }
            PaymentSDK.RESULT_ERROR -> {
                val errorCode = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                val message = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
                builder
                    .setMessage("Error code: $errorCode\nMessage: $message")
                    .setPositiveButton(R.string.ok_label) { _: DialogInterface?, _: Int -> }
            }
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}