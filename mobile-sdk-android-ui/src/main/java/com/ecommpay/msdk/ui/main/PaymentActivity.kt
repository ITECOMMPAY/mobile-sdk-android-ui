package com.ecommpay.msdk.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import com.ecommpay.msdk.core.MSDKCoreSession
import com.ecommpay.msdk.core.MSDKCoreSessionConfig
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.MessageAlert
import com.ecommpay.msdk.ui.base.MessageToast
import com.ecommpay.msdk.ui.base.ViewActions
import com.ecommpay.msdk.ui.navigation.NavigationState
import com.ecommpay.msdk.ui.theme.SDKTheme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main()
        }
    }

    companion object {

        lateinit var paymentInfo: PaymentInfo
        lateinit var initData: InitData
        lateinit var payment: Payment
        val config = MSDKCoreSessionConfig("pp-sdk.westresscode.net", "paymentpage.ecommpay.com")
        val msdkSession = MSDKCoreSession(config)


        fun buildPaymentIntent(context: Context, paymentInfo: PaymentInfo): Intent {
            this.paymentInfo = paymentInfo
            return Intent(context, PaymentActivity::class.java)
        }
    }

    @Composable
    private fun Main() {
        SDKTheme {
            val defaultActionListener: (ViewActions) -> Unit = { action ->
                when (action) {
                    is DefaultViewActions.ShowMessage -> {
                        when (val message = action.message) {
                            is MessageAlert -> {
                                Toast.makeText(
                                    this,
                                    message.message,
                                    Toast.LENGTH_LONG).show()
                            }
                            is MessageToast -> {
                                Toast.makeText(
                                    this,
                                    message.message,
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    is DefaultViewActions.SetResult -> {
                        setResult(action.resultCode)
                        finish()
                    }
                }
            }
            NavigationState(defaultActionListener = defaultActionListener)
        }
    }
}