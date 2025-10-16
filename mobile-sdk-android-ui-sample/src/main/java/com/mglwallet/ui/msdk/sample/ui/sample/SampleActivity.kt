package com.mglwallet.ui.msdk.sample.ui.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.mglwallet.msdk.ui.BuildConfig
import com.mglwallet.msdk.ui.EcmpPaymentInfo
import com.mglwallet.msdk.ui.Mglwallet
import com.mglwallet.msdk.ui.paymentOptions
import com.mglwallet.ui.msdk.sample.data.ProcessRepository
import com.mglwallet.ui.msdk.sample.domain.mappers.map
import com.mglwallet.ui.msdk.sample.domain.ui.base.MessageUI
import com.mglwallet.ui.msdk.sample.domain.ui.base.viewUseCase
import com.mglwallet.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import com.mglwallet.ui.msdk.sample.domain.ui.sample.SampleViewUC
import com.mglwallet.ui.msdk.sample.utils.SignatureGenerator
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import kotlinx.serialization.json.Json

class SampleActivity : ComponentActivity() {
    private lateinit var viewUseCase: SampleViewUC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewUseCase = viewUseCase("Sample") { SampleViewUC() }

        @OptIn(ExperimentalComposeUiApi::class)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            testTagsAsResourceId = true
                        },
                    color = MaterialTheme.colors.background
                ) {
                    SampleState(viewUseCase = viewUseCase)
                }
            }
        }
    }

    fun startPaymentPage() {
        val repositoryPaymentData = ProcessRepository.paymentData
        val mockModeType = ProcessRepository.mockModeType
        val startActionType = ProcessRepository.actionType
        val additionalFieldsToSend = ProcessRepository.additionalFields
        val screenDisplayModesToSend = ProcessRepository.screenDisplayModes
        val recurrentDataToSend = ProcessRepository.recurrentData?.map()
        val recipientDataToSend = ProcessRepository.recipientData?.map()
        val threeDSecureInfoToSend = ProcessRepository.commonJson?.threeDSecureInfo?.map()
        val ecmpPaymentInfo = EcmpPaymentInfo(
            forcePaymentMethod = repositoryPaymentData.forcePaymentMethod,
            hideSavedWallets = repositoryPaymentData.hideSavedWallets,
            projectId = repositoryPaymentData.projectId,
            paymentId = repositoryPaymentData.paymentId,
            paymentAmount = repositoryPaymentData.paymentAmount,
            paymentCurrency = repositoryPaymentData.paymentCurrency,
            customerId = repositoryPaymentData.customerId,
            paymentDescription = repositoryPaymentData.paymentDescription,
            languageCode = repositoryPaymentData.languageCode,
            regionCode = repositoryPaymentData.regionCode,
            token = repositoryPaymentData.token,
            ecmpThreeDSecureInfo = threeDSecureInfoToSend
        )
        ecmpPaymentInfo.signature =
            SignatureGenerator.generateSignature(
                ecmpPaymentInfo.getParamsForSignature(), repositoryPaymentData.secretKey
            )
        val paymentOptions = paymentOptions {
            //payment configuration
            paymentInfo = ecmpPaymentInfo
            actionType = startActionType
            recurrentData = recurrentDataToSend
            recipientInfo = recipientDataToSend
            screenDisplayModes {
                screenDisplayModesToSend.forEach {
                    mode(it)
                }
            }
            additionalFields {
                additionalFieldsToSend.map {
                    field {
                        type = it.type
                        value = it.value
                    }
                }
            }
            //hide scanning cards icon in the payment form
            hideScanningCards = ProcessRepository.hideScanningCards
            //google pay configuration
            merchantId = repositoryPaymentData.merchantId
            merchantName = repositoryPaymentData.merchantName
            isTestEnvironment = true
            //theme customization
            logoImage = ProcessRepository.bitmap
            brandColor = ProcessRepository.brandColor
            isDarkTheme = ProcessRepository.isDarkTheme
            //stored card type
            storedCardType = ProcessRepository.storedCardType
        }

        val sdk = Mglwallet(
            context = this.applicationContext,
            paymentOptions = paymentOptions,
            mockModeType = mockModeType,
        )

        if (BuildConfig.DEBUG) {
            sdk.intent.putExtra(
                Mglwallet.EXTRA_API_HOST,
                ProcessRepository.paymentData.apiHost
            )
            sdk.intent.putExtra(
                Mglwallet.EXTRA_WS_API_HOST,
                ProcessRepository.paymentData.wsApiHost
            )
        }

        startActivityForResult.launch(sdk.intent)
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            when (result.resultCode) {
                Mglwallet.RESULT_SUCCESS -> {
                    val payment = Json.decodeFromString<Payment?>(
                        data?.getStringExtra(Mglwallet.EXTRA_PAYMENT).toString()
                    )
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

                Mglwallet.RESULT_CANCELLED -> {
                    viewUseCase.pushIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Dialogs.Info.Cancelled(
                                "You cancelled the payment"
                            )
                        )
                    )
                }

                Mglwallet.RESULT_DECLINE -> {
                    viewUseCase.pushIntent(
                        SampleViewIntents.ShowMessage(
                            MessageUI.Dialogs.Info.Decline(
                                "Your payment was declined"
                            )
                        )
                    )
                }

                Mglwallet.RESULT_ERROR -> {
                    val errorCode = data?.getStringExtra(Mglwallet.EXTRA_ERROR_CODE)
                    val message = data?.getStringExtra(Mglwallet.EXTRA_ERROR_MESSAGE)
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
