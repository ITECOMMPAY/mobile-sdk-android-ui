package com.ecommpay.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.SDKDarkColorPalette
import com.ecommpay.msdk.ui.theme.SDKDarkTypography
import com.ecommpay.msdk.ui.theme.SDKTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SDKTopBar(
    title: String,
    arrowIcon: ImageVector? = Icons.Filled.ArrowBack,
    listener: () -> Unit = {},
) {
    Surface(
        color = SDKTheme.colors.backgroundTopBar,
        contentColor = SDKTheme.colors.topBarCloseButton
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = SDKTheme.typography.s22Bold,
                text = title
            )
            Spacer(modifier = Modifier.weight(1f))
            if (arrowIcon != null) {
                //Получаем контроллер системной клавиатуры
                val keyboardController = LocalSoftwareKeyboardController.current
                Image(
                    modifier = Modifier
                        .clickable(
                            indication = null, //отключаем анимацию при клике
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                keyboardController?.hide()
                                listener()
                            }
                        )
                        .padding(end = 35.dp),
                    imageVector = arrowIcon,
                    colorFilter = ColorFilter.tint(SDKTheme.colors.topBarCloseButton),
                    contentDescription = "",
                )
            }
            Image(
                modifier = Modifier.clickable(
                    indication = null, //отключаем анимацию при клике
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        listener()
                    }
                ),
                imageVector = Icons.Default.Close,
                colorFilter = ColorFilter.tint(SDKTheme.colors.topBarCloseButton),
                contentDescription = "",
            )
        }
    }
}

@Composable
@Preview
fun PreviewLightToolbar() {
    SDKTheme() {
        SDKTopBar("Payment Methods")
    }
}

@Composable
@Preview
fun PreviewDarkToolbar() {
    SDKTheme(
        colors = SDKDarkColorPalette,
        typography = SDKDarkTypography,
    ) {
        SDKTopBar(
            title = "Payment Methods",
            arrowIcon = null)
    }
}

