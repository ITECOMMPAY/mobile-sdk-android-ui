package com.paymentpage.msdk.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.MSDKCoreSessionConfig
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.core.domain.entities.RecurrentInfo
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecureInfo
import com.paymentpage.msdk.core.manager.resource.strings.StringResourceManager
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.navigation.NavigationComponent
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.presentation.main.MainViewModel
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.viewModelFactory

class PaymentActivity : ComponentActivity(), PaymentDelegate {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("ResourceAsColor")
    public override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
            window.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }
        super.onCreate(savedInstanceState)

        var config = MSDKCoreSessionConfig.release(BuildConfig.API_HOST, BuildConfig.WS_API_HOST)
        if (BuildConfig.DEBUG) {
            val isMockModeEnabled = intent.getBooleanExtra(Constants.EXTRA_MOCK_MODE_ENABLED, false)
            config = when {
                isMockModeEnabled -> MSDKCoreSessionConfig.mockFullSuccessFlow()
                else -> MSDKCoreSessionConfig.debug(
                    intent.getStringExtra(Constants.EXTRA_API_HOST).toString(),
                    intent.getStringExtra(Constants.EXTRA_WS_API_HOST).toString()
                )
            }
        }
        msdkSession = MSDKCoreSession(config)

        setContent {
            var drawerState by remember { mutableStateOf(BottomDrawerState(initialValue = BottomDrawerValue.Closed)) }
            LaunchedEffect(Unit) {
                drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded)
            }

            BottomDrawer(
                modifier = Modifier.wrapContentSize(),
                drawerContent = {
                    SDKTheme() {
                        SDKCommonProvider(
                            paymentInfo = paymentInfo,
                            recurrentInfo = recurrentInfo,
                            threeDSecureInfo = threeDSecureInfo,
                            recipientInfo = recipientInfo,
                            additionalFields = additionalFields,
                            msdkSession = msdkSession,
                        ) {
                            NavigationComponent(
                                navigator = navigator,
                                delegate = this@PaymentActivity
                            )
                        }
                    }
                },
                drawerState = drawerState,
                drawerBackgroundColor = Color.Transparent,
                gesturesEnabled = false,
                drawerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f)),
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        msdkSession.cancel()
    }

    override fun onError(code: ErrorCode, message: String?) {
        val dataIntent = Intent()
        dataIntent.putExtra(
            Constants.EXTRA_ERROR_CODE, code.name
        )
        dataIntent.putExtra(
            Constants.EXTRA_ERROR_MESSAGE, message
        )
        setResult(Constants.RESULT_ERROR, dataIntent)
        finish()
    }

    override fun onCompleteWithSuccess(payment: Payment) {
        val dataIntent = Intent()
        setResult(Constants.RESULT_SUCCESS, dataIntent)
        finish()
    }

    override fun onCompleteWithFail(status: String?, payment: Payment) {
        val dataIntent = Intent()
        dataIntent.putExtra(
            Constants.EXTRA_PAYMENT_STATUS, payment.serverStatusName
        )
        setResult(Constants.RESULT_FAILED, dataIntent)
        finish()
    }

    override fun onCompleteWithDecline(payment: Payment) {
        val dataIntent = Intent()
        setResult(Constants.RESULT_DECLINE, dataIntent)
        finish()
    }

    override fun onCancel() {
        val dataIntent = Intent()
        setResult(Constants.RESULT_CANCELLED, dataIntent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    companion object {
        private val navigator = Navigator()
        private lateinit var paymentInfo: PaymentInfo
        private var recurrentInfo: RecurrentInfo? = null
        private var threeDSecureInfo: ThreeDSecureInfo? = null
        private var recipientInfo: RecipientInfo? = null
        private var additionalFields: List<AdditionalField> = emptyList()
        var logoImage: Bitmap? = null

        private lateinit var msdkSession: MSDKCoreSession
        val stringResourceManager: StringResourceManager
            get() = msdkSession.getStringResourceManager()

        fun buildPaymentIntent(
            context: Context,
            paymentInfo: PaymentInfo,
            recurrentInfo: RecurrentInfo? = null,
            threeDSecureInfo: ThreeDSecureInfo? = null,
            recipientInfo: RecipientInfo? = null,
            additionalFields: List<AdditionalField> = emptyList(),
            logoImage: Bitmap? = null
        ): Intent {
            this.paymentInfo = paymentInfo
            this.recurrentInfo = recurrentInfo
            this.threeDSecureInfo = threeDSecureInfo
            this.recipientInfo = recipientInfo
            this.additionalFields = additionalFields
            this.logoImage = logoImage

            val intent = Intent(context, PaymentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            return intent
        }
    }

}