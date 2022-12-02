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
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    notScrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    scrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    onClose: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .background(SDKTheme.colors.backgroundColor)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
            .fillMaxWidth()
            .padding(top = 25.dp, start = 25.dp, end = 25.dp),
        content = {
            if (title != null || onClose != null || onBack != null) {
                SDKTopBar(
                    title = title,
                    onClose = onClose,
                    onBack = onBack
                )
                Spacer(modifier = Modifier.size(15.dp))
            }
            if (notScrollableContent != null) {
                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    content = notScrollableContent,
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment,
                )
            }
            if (scrollableContent != null) {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    content = scrollableContent,
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment,
                )
            }
        }
    )
}
