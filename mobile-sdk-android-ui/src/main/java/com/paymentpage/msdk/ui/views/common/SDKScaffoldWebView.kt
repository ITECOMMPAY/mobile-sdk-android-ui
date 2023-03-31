package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.theme.SDKTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SDKScaffoldWebView(
    modifier: Modifier = Modifier,
    title: String? = null,
    notScrollableContent: @Composable ColumnScope.() -> Unit,
    showCancelButton: Boolean = true,
    onClose: () -> Unit,
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
                .semantics {
                    invisibleToUser()
                }
        )
        Column(
            modifier = Modifier
                .background(
                    color = SDKTheme.colors.background,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                .fillMaxWidth(),
            content = {
                SDKTopBar(
                    modifier = Modifier.padding(
                        top = 25.dp,
                        bottom = 15.dp,
                        start = 25.dp,
                        end = 25.dp
                    ),
                    title = title,
                    onClose = if (showCancelButton) onClose else null,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(SDKTheme.colors.inputField)
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    content = notScrollableContent,
                )
            }
        )
    }
}
