package com.paymentpage.ui.msdk.sample.ui.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.PaymentSDK
import com.paymentpage.msdk.ui.SDKPaymentOptions
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.ui.msdk.sample.data.ProcessRepository
import com.paymentpage.ui.msdk.sample.domain.mappers.map
import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI
import com.paymentpage.ui.msdk.sample.domain.ui.base.viewUseCase
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewUC
import com.paymentpage.ui.msdk.sample.utils.SignatureGenerator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SampleActivity : ComponentActivity() {
    private lateinit var viewUseCase: SampleViewUC
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewUseCase = viewUseCase("Sample", { SampleViewUC() })

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SampleState(viewUseCase = viewUseCase)
                }
            }
        }
    }

    fun startPaymentPage() {
        val repositoryPaymentData = ProcessRepository.paymentData
        val additionalFieldsToSend = ProcessRepository.additionalFields
        val recurrentDataToSend = ProcessRepository.recurrentData
        val threeDSecureInfoToSend = ProcessRepository.commonJson?.threeDSecureInfo
        val paymentInfo = PaymentInfo(
            forcePaymentMethod = repositoryPaymentData.forcePaymentMethod.ifEmpty { null },
            hideSavedWallets = repositoryPaymentData.hideSavedWallets,
            projectId = repositoryPaymentData.projectId,
            paymentId = repositoryPaymentData.paymentId,
            paymentAmount = repositoryPaymentData.paymentAmount,
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
            actionType = repositoryPaymentData.actionType,
            logoImage = repositoryPaymentData.bitmap,
            brandColor = repositoryPaymentData.brandColor,
            merchantId = repositoryPaymentData.merchantId,
            merchantName = repositoryPaymentData.merchantName,
            recurrentInfo = recurrentDataToSend.map(),
            additionalFields = additionalFieldsToSend
        )

        val sdk = PaymentSDK(
            context = this.applicationContext,
            paymentOptions = paymentOptions,
            mockModeType = repositoryPaymentData.mockModeType,
            apiHost = repositoryPaymentData.apiHost,
            wsApiHost = repositoryPaymentData.wsApiHost
        )
        startActivityForResult.launch(sdk.intent)
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            when (result.resultCode) {
                PaymentSDK.RESULT_SUCCESS -> {
                    val payment = Json.decodeFromString<Payment?>(data?.getStringExtra(Constants.EXTRA_PAYMENT).toString())
                    when {
                        payment?.token != null -> {
                            viewUseCase.pushIntent(
                                SampleViewIntents.ShowMessage(
                                    MessageUI.Dialogs.Info.SuccessTokenize(
                                        payment.token!!
                                    )
                                )
                            )
                        }
                        else -> {
                            viewUseCase.pushIntent(
                                SampleViewIntents.ShowMessage(
                                    MessageUI.Dialogs.Info.Success(
                                        "Your payment is successful"
                                    )
                                )
                            )
                        }
                    }
                }
                PaymentSDK.RESULT_CANCELLED -> {
                    viewUseCase.pushIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Dialogs.Info.Cancelled(
                                "You cancelled the payment"
                            )
                        )
                    )
                }
                PaymentSDK.RESULT_DECLINE -> {
                    viewUseCase.pushIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Dialogs.Info.Decline(
                                "Your payment was declined"
                            )
                        )
                    )
                }
                PaymentSDK.RESULT_ERROR -> {
                    val errorCode = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_CODE)
                    val message = data?.getStringExtra(PaymentSDK.EXTRA_ERROR_MESSAGE)
                    viewUseCase.pushIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Dialogs.Info.Error(
                                "Error code: $errorCode\nMessage: $message"
                            )
                        )
                    )
                }
            }
        }
}
