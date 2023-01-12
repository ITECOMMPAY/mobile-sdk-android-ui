package com.paymentpage.msdk.ui.presentation.main.screens.aps

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.presentation.main.saleAps
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffoldWebView

@Composable
internal fun ApsScreen(onCancel: () -> Unit) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val method =
        paymentMethodsViewModel.lastState.currentMethod as UIPaymentMethod.UIApsPaymentMethod
    val apsMethod = mainViewModel.lastState.apsPageState?.apsMethod
    val paymentUrl = apsMethod?.paymentUrl
    BackHandler(true) { onCancel() }

    SDKScaffoldWebView(
        title = method.title,
        notScrollableContent = {
            if (paymentUrl != null) {
                ApsPageView(
                    method = method,
                    paymentUrl = paymentUrl
                )
            }
        },
        onClose = { onCancel() }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun ApsPageView(
    method: UIPaymentMethod.UIApsPaymentMethod,
    paymentUrl: String,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    var isLoading by remember { mutableStateOf(false) }
    if (isLoading)
        Box(
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                .fillMaxWidth()
                .background(SDKTheme.colors.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = SDKTheme.colors.brand
            )
        }
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(modifier = Modifier
            .weight(1f)
            .background(SDKTheme.colors.backgroundColor),
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String?,
                            favicon: Bitmap?,
                        ) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                            if (url?.startsWith(paymentUrl) == false) {
                                paymentMethodsViewModel.setCurrentMethod(method)
                                mainViewModel.saleAps(method)
                            }
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                    settings.javaScriptEnabled = true
                    settings.builtInZoomControls = true
                    settings.domStorageEnabled = true

                    loadUrl(paymentUrl)
                }
            }, update = {

            })
    }
}