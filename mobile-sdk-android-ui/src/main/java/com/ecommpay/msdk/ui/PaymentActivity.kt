package com.ecommpay.msdk.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.core.MSDKCoreSession
import com.ecommpay.msdk.core.MSDKCoreSessionConfig
import com.ecommpay.msdk.core.base.ErrorCode
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.ui.navigation.NavigationComponent
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.theme.*

internal class PaymentActivity : ComponentActivity(), PaymentDelegate {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("ResourceAsColor")
    public override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
            window.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }
        super.onCreate(savedInstanceState)

        setContent {
            BottomDrawer(
                modifier = Modifier.wrapContentSize(),
                drawerContent = {
                    SDKTheme( ) {
                        Box(contentAlignment = Alignment.BottomCenter) {
                            NavigationComponent(
                                navigator = navigator,
                                delegate = this@PaymentActivity
                            )
                        }
                    }
                },
                drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded),
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

    companion object {
        var paymentOptions: PaymentOptions? = null
        private val config = MSDKCoreSessionConfig.nl3WithDebug()
        val msdkSession = MSDKCoreSession(config)
        val stringResourceManager = msdkSession.getStringResourceManager()
        val navigator = Navigator()


        fun buildPaymentIntent(context: Context, paymentOptions: PaymentOptions): Intent {
            this.paymentOptions = paymentOptions
            return Intent(context, PaymentActivity::class.java)
        }
    }

    override fun onError(code: ErrorCode, message: String?) {
        val dataIntent = Intent()
        dataIntent.putExtra(
            PaymentSDK.EXTRA_ERROR_CODE, code.name
        )
        dataIntent.putExtra(
            PaymentSDK.EXTRA_ERROR_MESSAGE, message
        )
        setResult(PaymentSDK.RESULT_ERROR, dataIntent)
        finish()
    }

    override fun onCompleteWithSuccess(payment: Payment) {
        val dataIntent = Intent()
        setResult(PaymentSDK.RESULT_SUCCESS, dataIntent)
        finish()
    }

    override fun onCompleteWithFail(status: String?, payment: Payment) {
        val dataIntent = Intent()
        dataIntent.putExtra(
            PaymentSDK.EXTRA_PAYMENT_STATUS, payment.serverStatusName
        )
        setResult(PaymentSDK.RESULT_FAILED, dataIntent)
        finish()
    }

    override fun onCompleteWithDecline(payment: Payment) {
        val dataIntent = Intent()
        setResult(PaymentSDK.RESULT_DECLINE, dataIntent)
        finish()
    }

}