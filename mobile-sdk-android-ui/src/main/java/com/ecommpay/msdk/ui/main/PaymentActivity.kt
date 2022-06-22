package com.ecommpay.msdk.ui.main

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.ecommpay.msdk.core.domain.entities.PaymentInfo
import com.ecommpay.msdk.core.domain.entities.payment.Payment
import com.ecommpay.msdk.ui.base.DefaultViewActions
import com.ecommpay.msdk.ui.base.MessageAlert
import com.ecommpay.msdk.ui.base.MessageToast
import com.ecommpay.msdk.ui.base.ViewActions
import com.ecommpay.msdk.ui.navigation.NavigationState
import com.ecommpay.msdk.ui.theme.*

class PaymentActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
            window.setBackgroundDrawable(ColorDrawable(R.color.transparent))
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
//            BottomSheetScaffold(
//                sheetContent = {
//                    SDKTheme { NavigationState(defaultActionListener = defaultActionListener) }
//                },
//                scaffoldState = bottomSheetScaffoldState,
//                sheetGesturesEnabled = false,
//                drawerGesturesEnabled = false,
//                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
//                backgroundColor = Color.Transparent,
//                sheetPeekHeight = 0.dp,
//                drawerContent = {
//
//                }
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(it)
//                        .background(Color.Transparent),
//                    color = Color.Black.copy(alpha = 0.2f)
//                ) {
//
//                }
//            }
            BottomDrawer(
                modifier = Modifier.wrapContentHeight(),
                drawerContent = {
                    SDKTheme(
                        colors = colors,
                        typography = typography
                    ) { NavigationState(defaultActionListener = defaultActionListener) }
                },
                drawerState = BottomDrawerState(initialValue = BottomDrawerValue.Expanded),
                drawerBackgroundColor = Color.Transparent,
                gesturesEnabled = false,
                drawerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
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


    companion object {
        lateinit var paymentInfo: PaymentInfo
        lateinit var payment: Payment
        val config = MSDKCoreSessionConfig("pp-sdk.westresscode.net", "paymentpage.ecommpay.com")
        val msdkSession = MSDKCoreSession(config)
        val stringResourceManager = msdkSession.getStringResourceManager()


        fun buildPaymentIntent(context: Context, paymentInfo: PaymentInfo): Intent {
            this.paymentInfo = paymentInfo
            return Intent(context, PaymentActivity::class.java)
        }
    }
}