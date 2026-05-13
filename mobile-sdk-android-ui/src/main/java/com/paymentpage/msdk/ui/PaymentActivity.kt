package com.paymentpage.msdk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.core.view.WindowCompat
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
import java.lang.ref.WeakReference
import java.util.UUID

class PaymentActivity : ComponentActivity(), PaymentDelegate {
    private lateinit var sessionId: String
    private lateinit var sessionState: SessionState

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionId = intent.getStringExtra(EXTRA_SESSION_ID) ?: run {
            Log.e("SDK_ERROR", "Session id is empty - this will cause crash!")
            onError(code = ErrorCode.ILLEGAL_STATE, message = "Session id is empty")
            return
        }

        val launchMockModeType =
            (intent.getSerializableExtra(Constants.EXTRA_MOCK_MODE_TYPE) as? SDKMockModeType)
                ?: SDKMockModeType.DISABLED
        val apiHost = intent.getStringExtra(Constants.EXTRA_API_HOST)
        val wsApiHost = intent.getStringExtra(Constants.EXTRA_WS_API_HOST)

        sessionState = obtainOrCreateSessionState(
            sessionId = sessionId,
            mockModeType = launchMockModeType,
            apiHost = apiHost,
            wsApiHost = wsApiHost
        ) ?: run {
            onError(code = ErrorCode.ILLEGAL_STATE, message = "Payment options is empty")
            return
        }

        activeActivity = WeakReference(this)

        mockModeType = sessionState.mockModeType
        paymentOptions = sessionState.paymentOptions
        msdkSession = sessionState.msdkSession

        val localPaymentOptions = sessionState.paymentOptions

        crashHandler = CrashHandler(
            projectId = localPaymentOptions.paymentInfo.projectId.toLong(),
            paymentId = localPaymentOptions.paymentInfo.paymentId,
            customerId = localPaymentOptions.paymentInfo.customerId,
            signature = localPaymentOptions.paymentInfo.signature,
            errorInteractor = msdkSession.getErrorEventInteractor(),
        )

        crashHandler?.start(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Box {
                MainContent(
                    activity = this@PaymentActivity,
                    paymentOptions = localPaymentOptions,
                    msdkSession = msdkSession,
                )
            }
        }
    }

    override fun onDestroy() {
        if (activeActivity?.get() === this) {
            activeActivity = null
        }

        // Keep session alive across configuration changes and clear it only when payment flow really finishes.
        if (isFinishing && !isChangingConfigurations) {
            clearSessionState(sessionId = sessionId)
        }

        super.onDestroy()
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
        private const val EXTRA_SESSION_ID = "payment_session_id"

        internal var paymentOptions: SDKPaymentOptions? = null
        internal var crashHandler: CrashHandler? = null

        var mockModeType = SDKMockModeType.DISABLED

        internal lateinit var msdkSession: MSDKCoreSession
        val stringResourceManager: StringResourceManager
            get() = msdkSession.getStringResourceManager()

        @Volatile
        private var activeSessionState: SessionState? = null

        @Volatile
        private var activeSessionId: String? = null

        @Volatile
        private var activeActivity: WeakReference<PaymentActivity>? = null

        @Synchronized
        private fun obtainOrCreateSessionState(
            sessionId: String,
            mockModeType: SDKMockModeType,
            apiHost: String?,
            wsApiHost: String?
        ): SessionState? {
            if (activeSessionId == sessionId && activeSessionState != null) {
                return activeSessionState
            }

            val localPaymentOptions = paymentOptions ?: run {
                Log.e("SDK_ERROR", "Payment options is empty - this will cause crash!")
                return null
            }

            val config = when {
                mockModeType == SDKMockModeType.SUCCESS -> MSDKCoreSessionConfig.mockFullSuccessFlow(
                    duration = Duration.millis(Constants.THREE_D_SECURE_REDIRECT_DURATION)
                )

                mockModeType == SDKMockModeType.DECLINE -> MSDKCoreSessionConfig.mockFullDeclineFlow(
                    duration = Duration.millis(Constants.THREE_D_SECURE_REDIRECT_DURATION)
                )

                BuildConfig.DEBUG -> MSDKCoreSessionConfig.debug(
                    apiHost = apiHost ?: BuildConfig.API_HOST,
                    wsApiHost = wsApiHost ?: BuildConfig.WS_API_HOST
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

            val state = SessionState(
                paymentOptions = localPaymentOptions,
                mockModeType = mockModeType,
                msdkSession = MSDKCoreSession(config)
            )
            activeSessionId = sessionId
            activeSessionState = state
            return state
        }

        @Synchronized
        private fun clearSessionState(sessionId: String) {
            if (activeSessionId != sessionId) return

            activeSessionState?.msdkSession?.cancel()
            activeSessionState = null
            activeSessionId = null
            activeActivity = null
            paymentOptions = null
            crashHandler = null
        }

        @JvmStatic
        fun closeActivePaymentSession(): Boolean {
            val activity = activeActivity?.get() ?: return false
            if (activity.isDestroyed || activity.isFinishing) return false

            activity.runOnUiThread {
                if (!activity.isDestroyed && !activity.isFinishing) {
                    activity.onCancel()
                }
            }
            return true
        }

        fun buildPaymentIntent(
            context: Context,
            paymentOptions: SDKPaymentOptions,
            mockModeType: SDKMockModeType,
        ) = Intent(context, PaymentActivity::class.java).apply {
            this@Companion.paymentOptions = paymentOptions
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            putExtra(Constants.EXTRA_MOCK_MODE_TYPE, mockModeType)
            putExtra(EXTRA_SESSION_ID, UUID.randomUUID().toString())
        }
    }
}

private data class SessionState(
    val paymentOptions: SDKPaymentOptions,
    val mockModeType: SDKMockModeType,
    val msdkSession: MSDKCoreSession
)
