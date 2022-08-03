package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme


@Composable
internal fun SDKScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    notScrollableContent: @Composable () -> Unit = {},
    scrollableContent: @Composable () -> Unit = {},
    footerContent: @Composable () -> Unit = {},
    onClose: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .background(SDKTheme.colors.backgroundColor)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
            .fillMaxWidth(),
        content = {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
                    SDKTopBar(
                        title = title,
                        onClose = onClose,
                        onBack = onBack
                    )
                }
                Column(
                    modifier = modifier
                ) {
                    notScrollableContent()
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        scrollableContent()
                        footerContent()
                    }
                }
            }
        }
    )
}