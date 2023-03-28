package com.paymentpage.msdk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        super.onCreate(savedInstanceState)

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
                apiHost = intent.getStringExtra(Constants.EXTRA_API_HOST) ?: apiHost,
                wsApiHost = intent.getStringExtra(Constants.EXTRA_WS_API_HOST) ?: wsApiHost
            )
            else -> MSDKCoreSessionConfig.release(apiHost = apiHost, wsApiHost = wsApiHost)
        }
        config.userAgentData =
            UserAgentData(
                applicationInfo = ApplicationInfo(
                    version = BuildConfig.SDK_VERSION_NAME
                )
            )
        msdkSession = MSDKCoreSession(config)

        if (!BuildConfig.DEBUG)
            with(paymentOptions.paymentInfo) {
                CrashHandler(
                    projectId = projectId.toLong(),
                    paymentId = paymentId,
                    customerId = customerId,
                    signature = signature,
                    errorInteractor = msdkSession.getErrorEventInteractor()
                )
            }.start(context = this@PaymentActivity)

        setContent {
            MainContent(
                activity = this@PaymentActivity,
                paymentOptions = paymentOptions,
                msdkSession = msdkSession,
            )
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

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    companion object {

        internal lateinit var paymentOptions: SDKPaymentOptions

        internal lateinit var apiHost: String
        internal lateinit var wsApiHost: String

        var mockModeType = SDKMockModeType.DISABLED

        internal lateinit var msdkSession: MSDKCoreSession
        val stringResourceManager: StringResourceManager
            get() = msdkSession.getStringResourceManager()

        fun buildPaymentIntent(
            context: Context,
            apiHost: String,
            wsApiHost: String,
            paymentOptions: SDKPaymentOptions,
            mockModeType: SDKMockModeType,
        ) = Intent(context, PaymentActivity::class.java).apply {
            this@Companion.paymentOptions = paymentOptions
            this@Companion.apiHost = apiHost
            this@Companion.wsApiHost = wsApiHost
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            putExtra(Constants.EXTRA_MOCK_MODE_TYPE, mockModeType)
        }
    }
}