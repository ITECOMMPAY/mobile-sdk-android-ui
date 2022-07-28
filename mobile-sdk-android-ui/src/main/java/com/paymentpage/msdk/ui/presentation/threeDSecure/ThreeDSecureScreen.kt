package com.paymentpage.msdk.ui.presentation.threeDSecure

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paymentpage.msdk.core.domain.entities.threeDSecure.AcsPage
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.presentation.main.threeDSecureHandled
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun ThreeDSecureScreen(
    onCancel: () -> Unit,
) {
    val viewModel = LocalMainViewModel.current
    val acsPage = viewModel.lastState.acsPageState?.acsPage

    BackHandler(true) { onCancel() }

    SDKScaffold(
        notScrollableContent = {
            Spacer(modifier = Modifier.fillMaxWidth().height(2.dp).background(SDKTheme.colors.panelBackgroundColor))
            if (acsPage != null) {
                AcsPageView(acsPage = acsPage)
            }
        },
        scrollableContent = {},
        footerContent = {},
        onClose = { onCancel() }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun AcsPageView(
    acsPage: AcsPage,
) {
    val viewModel = LocalMainViewModel.current
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
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            isLoading = newProgress < 100
                        }
                    }
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String?,
                            favicon: Bitmap?,
                        ) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true
                            if (url.equals(acsPage.acs?.termUrl)) {
                                viewModel.threeDSecureHandled()
                            }
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?,
                        ) {
                            super.onReceivedSslError(view, handler, error)

                        }
                    }
                    settings.javaScriptEnabled = true
                    loadDataWithBaseURL(
                        acsPage.acs?.acsUrl ?: "",
                        acsPage.content ?: "",
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }, update = {

            })
    }
}