package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun SelectItemsDialog(
    modifier: Modifier = Modifier,
    items: Map<String?, String?>,
    dialogShape: Shape = SDKTheme.shapes.radius12,
    itemShape: Shape = RectangleShape,
    backgroundColor: Color = SDKTheme.colors.background,
    onDismissRequest: () -> Unit,
    onClickItem: (String) -> Unit,
) {
    Dialog(
        content = {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(shape = dialogShape)
            ) {
                Column(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .background(color = backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items.forEach { (key, value) ->
                        if (key != null && value != null) {
                            CustomButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    onClickItem(key)
                                },
                                secondaryColor = backgroundColor,
                                isEnabled = true,
                                isRightArrowVisible = false,
                                content = {
                                    Text(
                                        text = key,
                                        style = SDKTheme.typography.s16Normal
                                    )
                                },
                                shape = itemShape
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        color = SDKTheme.colors.highlight
                                    )
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = onDismissRequest
    )
}