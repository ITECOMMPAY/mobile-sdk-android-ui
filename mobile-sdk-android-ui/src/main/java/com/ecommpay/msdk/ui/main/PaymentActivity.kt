package com.ecommpay.msdk.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
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
                }
            }
            NavigationState(defaultActionListener = defaultActionListener)
        }
    }

    companion object {
        lateinit var paymentInfo: PaymentInfo

        @Composable
        fun buildPaymentIntent(paymentInfo: PaymentInfo): Intent {
            this.paymentInfo = paymentInfo
            val context = LocalContext.current
            return Intent(context, PaymentActivity::class.java)
        }
    }
}