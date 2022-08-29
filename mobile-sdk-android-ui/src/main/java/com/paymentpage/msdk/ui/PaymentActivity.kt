package com.paymentpage.msdk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ecommpay.msdk.ui.EcmpPaymentSDK
import com.ecommpay.msdk.ui.EcmpPaymentSDK.MockModeType.*
import com.paymentpage.msdk.core.ApplicationInfo
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.MSDKCoreSessionConfig
import com.paymentpage.msdk.core.UserAgentData
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.core.manager.resource.strings.StringResourceManager
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.presentation.MainContent

class PaymentActivity : ComponentActivity(), PaymentDelegate {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!BuildConfig.DEBUG)
            CrashHandler.init(this)
        mockModeType =
            intent.getSerializableExtra(Constants.EXTRA_MOCK_MODE_TYPE) as EcmpPaymentSDK.MockModeType
        val config = when {
            mockModeType == SUCCESS -> MSDKCoreSessionConfig.mockFullSuccessFlow()
            mockModeType == DECLINE -> MSDKCoreSessionConfig.mockFullDeclineFlow()
            BuildConfig.DEBUG -> MSDKCoreSessionConfig.debug(
                intent.getStringExtra(Constants.EXTRA_API_HOST).toString(),
                intent.getStringExtra(Constants.EXTRA_WS_API_HOST).toString()
            )
            else -> MSDKCoreSessionConfig.release(BuildConfig.API_HOST, BuildConfig.WS_API_HOST)
        }
        config.userAgentData = UserAgentData(applicationInfo = ApplicationInfo(version = BuildConfig.SDK_VERSION_NAME))
        msdkSession = MSDKCoreSession(config)

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

        private lateinit var paymentOptions: SDKPaymentOptions

        var mockModeType = DISABLED

        private lateinit var msdkSession: MSDKCoreSession
        val stringResourceManager: StringResourceManager
            get() = msdkSession.getStringResourceManager()

        fun buildPaymentIntent(
            context: Context,
            paymentOptions: SDKPaymentOptions,
            mockModeType: EcmpPaymentSDK.MockModeType,
        ): Intent {
            this.paymentOptions = paymentOptions

            val intent = Intent(context, PaymentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra(Constants.EXTRA_MOCK_MODE_TYPE, mockModeType)
            return intent
        }
    }
}