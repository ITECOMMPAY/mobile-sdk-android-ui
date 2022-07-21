package com.paymentpage.msdk.ui.presentation.threeDSecure

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CustomButton
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun ThreeDSecureScreen(
    onCancel: () -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val acsPage = viewModel.lastState.acsPageState?.acsPage

    BackHandler(true) { onCancel() }

    SDKScaffold(
        title = "3DS",
        notScrollableContent = { },
        scrollableContent = {
            if (PaymentActivity.isMockModeEnabled)
                CustomButton(
                    modifier = Modifier.height(45.dp),
                    isEnabled = true,
                    content = {
                        Text(
                            text = "Page Handled",
                            style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                        )

                    },
                    onClick = {
                        viewModel.threeDSecureHandled()
                    }
                )
            AndroidView(modifier = Modifier
                .fillMaxSize()
                .background(SDKTheme.colors.backgroundColor),
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        acsPage?.let { page ->
                            loadDataWithBaseURL(
                                page.acs?.acsUrl ?: "",
                                page.content ?: "",
                                "text/html",
                                "UTF-8",
                                null
                            )
                        }


                    }
                }, update = {

                })
        },
        footerContent = { },
        onClose = { onCancel() }
    )
}
