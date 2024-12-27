package com.paymentpage.msdk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import com.paymentpage.msdk.core.ApplicationInfo
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.MSDKCoreSessionConfig
import com.paymentpage.msdk.core.UserAgentData
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.manager.resource.strings.StringResourceManager
import com.paymentpage.msdk.core.utils.Duration
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.MainContent

class PaymentActivity : ComponentActivity(), PaymentDelegate {

    public override fun onCreate(savedInstanceState: Bundle?) {

        mockModeType =
            intent.getSerializableExtra(Constants.EXTRA_MOCK_MODE_TYPE) as SDKMockModeType
        val config = when {
            mockModeType == SDKMockModeType.SUCCESS -> MSDKCoreSessionConfig.mockFullSuccessFlow(
                duration = Duration.millis(Constants.THREE_D_SECURE_REDIRECT_DURATION)
            )

            mockModeType == SDKMockModeType.DECLINE -> MSDKCoreSessionConfig.mockFullDeclineFlow(
                duration = Duration.millis(Constants.THREE_D_SECURE_REDIRECT_DURATION)
            )

            BuildConfig.DEBUG -> MSDKCoreSessionConfig.debug(
                apiHost = intent.getStringExtra(Constants.EXTRA_API_HOST)
                    ?: BuildConfig.API_HOST,
                wsApiHost = intent.getStringExtra(Constants.EXTRA_WS_API_HOST)
                    ?: BuildConfig.WS_API_HOST
            )

            else -> MSDKCoreSessionConfig.release(
                apiHost = BuildConfig.API_HOST,
                wsApiHost = BuildConfig.WS_API_HOST
            )
        }
        config.userAgentData =
            UserAgentData(
                applicationInfo = ApplicationInfo(
                    version = BuildConfig.SDK_VERSION_NAME
                )
            )
        msdkSession = MSDKCoreSession(config)

        super.onCreate(savedInstanceState)

        if (paymentOptions == null) {
            onError(code = ErrorCode.ILLEGAL_STATE, message = "Payment options is empty")
            return
        }
        setContent {
            Box {
                MainContent(
                    activity = this@PaymentActivity,
                    paymentOptions = paymentOptions!!,
                    msdkSession = msdkSession,
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
        dataIntent.putExtra(
            Constants.EXTRA_PAYMENT, payment.json
        )
        setResult(Constants.RESULT_SUCCESS, dataIntent)
        finish()
    }

    override fun onCompleteWithDecline(payment: Payment) {
        val dataIntent = Intent()
        dataIntent.putExtra(
            Constants.EXTRA_PAYMENT, payment.json
        )
        setResult(Constants.RESULT_DECLINE, dataIntent)
        finish()
    }


    override fun onCancel() {
        val dataIntent = Intent()
        setResult(Constants.RESULT_CANCELLED, dataIntent)
        finish()
    }

    companion object {

        internal var paymentOptions: SDKPaymentOptions? = null

        var mockModeType = SDKMockModeType.DISABLED

        internal lateinit var msdkSession: MSDKCoreSession
        val stringResourceManager: StringResourceManager
            get() = msdkSession.getStringResourceManager()

        fun buildPaymentIntent(
            context: Context,
            paymentOptions: SDKPaymentOptions,
            mockModeType: SDKMockModeType,
        ) = Intent(context, PaymentActivity::class.java).apply {
            this@Companion.paymentOptions = paymentOptions
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            putExtra(Constants.EXTRA_MOCK_MODE_TYPE, mockModeType)
        }
    }
}