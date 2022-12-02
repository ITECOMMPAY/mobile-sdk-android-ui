package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme


@Composable
internal fun SDKScaffoldWebView(
    modifier: Modifier = Modifier,
    title: String? = null,
    notScrollableContent: @Composable ColumnScope.() -> Unit,
    onClose: (() -> Unit),
) {
    Column(
        modifier = Modifier
            .background(SDKTheme.colors.backgroundColor)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
            .fillMaxWidth(),
        content = {
            SDKTopBar(
                modifier = Modifier.padding(top = 25.dp, bottom = 15.dp, start = 25.dp, end = 25.dp),
                title = title,
                onClose = onClose,
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SDKTheme.colors.panelBackgroundColor)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                content = notScrollableContent,
            )
        }
    )
}
