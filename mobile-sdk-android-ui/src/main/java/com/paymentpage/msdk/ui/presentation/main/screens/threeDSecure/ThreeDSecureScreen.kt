package com.paymentpage.msdk.ui.presentation.main.screens.threeDSecure

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePage
import com.paymentpage.msdk.core.domain.entities.threeDSecure.ThreeDSecurePageType
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.threeDSecureRedirectHandle
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffoldWebView
import com.paymentpage.msdk.ui.views.common.alertDialog.ErrorAlertDialog

@Composable
internal fun ThreeDSecureScreen(
    onCancel: () -> Unit,
) {
    val viewModel = LocalMainViewModel.current
    val threeDSecurePage = viewModel.lastState.threeDSecurePageState?.threeDSecurePage

    BackHandler(true) { onCancel() }

    SDKScaffoldWebView(
        notScrollableContent = {
            if (threeDSecurePage != null) {
                ThreeDSecurePageView(
                    threeDSecurePage = threeDSecurePage,
                    onCancel = onCancel
                )
            }
        },
        onClose = { onCancel() }
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun ThreeDSecurePageView(
    threeDSecurePage: ThreeDSecurePage,
    onCancel: () -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val brandColor = LocalPaymentOptions.current.primaryBrandColor

    var showSslWarning by remember { mutableStateOf(false) }
    var sslErrorHandler by remember { mutableStateOf<SslErrorHandler?>(null) }

    if (showSslWarning) {
        ErrorAlertDialog(
            title = stringResource(R.string.ssl_error_title),
            message = stringResource(R.string.ssl_error_message),
            confirmButtonText = stringResource(R.string.proceed_label),
            onConfirmButtonClick = {
                showSslWarning = false
                sslErrorHandler?.proceed()
            },
            onDismissRequest = {
                showSslWarning = false
                sslErrorHandler?.cancel()
                onCancel()
            },
            onDismissButtonClick = {
                showSslWarning = false
                sslErrorHandler?.cancel()
                onCancel()
            },
            brandColor = brandColor
        )
    }

    if (threeDSecurePage.type == ThreeDSecurePageType.THREE_DS_2_FRICTIONLESS)
        Box(
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                .fillMaxWidth()
                .background(SDKTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = SDKTheme.colors.primary
            )
        }
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .weight(1f)
                .background(SDKTheme.colors.background),
            factory = {
                WebView(it).apply {
                    clipToOutline = true
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
                            viewModel.threeDSecureRedirectHandle(url = url ?: "")
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?,
                        ) {
                            sslErrorHandler = handler
                            showSslWarning = true
                        }
                    }
                    settings.javaScriptEnabled = true
                    loadDataWithBaseURL(
                        threeDSecurePage.loadUrl ?: "",
                        threeDSecurePage.content ?: "",
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            }, update = {

            }
        )
    }
}
