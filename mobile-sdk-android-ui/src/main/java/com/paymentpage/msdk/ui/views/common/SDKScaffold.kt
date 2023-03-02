package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme


@Composable
internal fun SDKScaffold(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    title: String? = null,
    showCloseButton: Boolean = true,
    notScrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    scrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    onClose: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClose
                )
        )
        Column(
            modifier = Modifier
                .background(
                    color = SDKTheme.colors.background,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                .fillMaxWidth()
                .padding(top = 25.dp, start = 25.dp, end = 25.dp),
            content = {
                if (title != null || showCloseButton || onBack != null) {
                    SDKTopBar(
                        title = title,
                        showCloseButton = showCloseButton,
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
}

@Preview
@Composable
private fun SDKScaffoldPreview() {
    SDKScaffold(onClose = {})
}