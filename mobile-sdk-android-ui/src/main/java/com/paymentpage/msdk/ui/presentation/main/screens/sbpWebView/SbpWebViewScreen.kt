package com.paymentpage.msdk.ui.presentation.main.screens.sbpWebView

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffoldWebView
import java.net.URISyntaxException
import java.util.Locale

@Composable
internal fun SbpWebViewScreen(
    onCancel: () -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val method = paymentMethodsViewModel.lastState.currentMethod as? UIPaymentMethod.UISbpQrPaymentMethod
    val webViewData = mainViewModel.lastState.sbpWebViewData.orEmpty()

    BackHandler(true) { onCancel() }

    SDKScaffoldWebView(
        title = method?.title,
        notScrollableContent = {
            if (webViewData.isNotBlank()) {
                SbpWebViewPage(webViewData = webViewData)
            }
        },
        onClose = onCancel
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun SbpWebViewPage(webViewData: String) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .background(SDKTheme.colors.background),
        factory = { context ->
            WebView(context).apply {
                clipToOutline = true
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.builtInZoomControls = true

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        if (request?.isForMainFrame == false) return false
                        val targetUrl = request?.url?.toString() ?: return false
                        return handleTargetUrl(context = context, webView = view, targetUrl = targetUrl)
                    }

                    @Deprecated("Deprecated in Java")
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        val targetUrl = url ?: return false
                        return handleTargetUrl(context = context, webView = view, targetUrl = targetUrl)
                    }
                }

                loadUrl(webViewData)
            }
        }
    )
}

private fun handleTargetUrl(context: Context, webView: WebView?, targetUrl: String): Boolean {
    val uri = runCatching { Uri.parse(targetUrl) }.getOrNull() ?: return false
    return when (uri.scheme?.lowercase(Locale.ROOT)) {
        "http", "https" -> false
        "intent" -> openIntentUri(context = context, webView = webView, intentUrl = targetUrl)
        else -> openUriExternally(context = context, uri = uri)
    }
}

private fun openIntentUri(context: Context, webView: WebView?, intentUrl: String): Boolean {
    return try {
        val intent = Intent.parseUri(intentUrl, Intent.URI_INTENT_SCHEME)
            .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        val packageManager = context.packageManager

        when {
            intent.resolveActivity(packageManager) != null -> {
                context.startActivity(intent)
                true
            }

            !intent.getStringExtra(BROWSER_FALLBACK_URL_KEY).isNullOrBlank() -> {
                val fallbackUrl = intent.getStringExtra(BROWSER_FALLBACK_URL_KEY).orEmpty()
                val fallbackUri = Uri.parse(fallbackUrl)
                if (fallbackUri.scheme?.lowercase(Locale.ROOT) in HTTP_SCHEMES) {
                    webView?.loadUrl(fallbackUrl)
                    true
                } else {
                    openUriExternally(context = context, uri = fallbackUri)
                }
            }

            !intent.`package`.isNullOrBlank() -> openMarketByPackageName(
                context = context,
                packageName = intent.`package`.orEmpty()
            )

            else -> false
        }
    } catch (_: URISyntaxException) {
        false
    } catch (_: ActivityNotFoundException) {
        false
    }
}

private fun openUriExternally(context: Context, uri: Uri): Boolean {
    return try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
        true
    } catch (_: ActivityNotFoundException) {
        if (uri.scheme.equals("market", ignoreCase = true)) {
            val packageName = uri.getQueryParameter("id").orEmpty()
            if (packageName.isBlank()) {
                false
            } else {
                openUriExternally(
                    context = context,
                    uri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            }
        } else {
            false
        }
    }
}

private fun openMarketByPackageName(context: Context, packageName: String): Boolean {
    if (packageName.isBlank()) return false
    return openUriExternally(
        context = context,
        uri = Uri.parse("market://details?id=$packageName")
    )
}

private const val BROWSER_FALLBACK_URL_KEY = "browser_fallback_url"
private val HTTP_SCHEMES = setOf("http", "https")
