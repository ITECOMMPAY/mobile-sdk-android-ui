package com.ecommpay.msdk.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.core.MSDKCoreSession
import com.ecommpay.msdk.core.MSDKCoreSessionConfig
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.ui.navigation.NavigationComponent
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.theme.*

internal class PaymentActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
            window.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
        }
        super.onCreate(savedInstanceState)

        setContent {
            val colors = if (isSystemInDarkTheme()) {
                SDKDarkColorPalette
            } else {
                SDKLightColorPalette
            }
            val typography = if (isSystemInDarkTheme()) {
                SDKDarkTypography
            } else {
                SDKLightTypography
            }
            BottomDrawer(
                modifier = Modifier.wrapContentHeight(),
                drawerContent = {
                    SDKTheme(
                        colors = colors,
                        typography = typography
                    ) {
                        NavigationComponent(navigator = navigator)
                    }
                },
                drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded),
                drawerBackgroundColor = Color.Transparent,
                gesturesEnabled = false,
                drawerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    color = Color.Black.copy(alpha = 0.2f)
                ) {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        msdkSession.cancel()
    }

    companion object {
        var paymentInfo: PaymentInfo? = null
        var payment: Payment? = null
        private val config = MSDKCoreSessionConfig.nl3WithDebug()
        val msdkSession = MSDKCoreSession(config)
        val stringResourceManager = msdkSession.getStringResourceManager()
        private val navigator = Navigator()


        fun buildPaymentIntent(context: Context, paymentInfo: PaymentInfo): Intent {
            Companion.paymentInfo = paymentInfo
            return Intent(context, PaymentActivity::class.java)
        }
    }
}