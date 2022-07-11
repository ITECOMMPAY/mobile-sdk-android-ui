package com.ecommpay.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.SDKTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TopBar(
    title: String,
    //arrowIcon: ImageVector? = Icons.Filled.ArrowBack,
    onClose: () -> Unit = {},
) {
    Surface(
        color = SDKTheme.colors.backgroundColor,
        contentColor = SDKTheme.colors.iconColor
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
//            if (arrowIcon != null) {
//                //Получаем контроллер системной клавиатуры
//                val keyboardController = LocalSoftwareKeyboardController.current
//                Image(
//                    modifier = Modifier
//                        .clickable(
//                            indication = null, //отключаем анимацию при клике
//                            interactionSource = remember { MutableInteractionSource() },
//                            onClick = {
//                                keyboardController?.hide()
//                            }
//                        )
//                        .padding(end = 35.dp),
//                    imageVector = arrowIcon,
//                    colorFilter = ColorFilter.tint(SDKTheme.colors.iconColor),
//                    contentDescription = "",
//                )
//            }
            Image(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        onClose()
                    }
                ),
                imageVector = Icons.Default.Close,
                colorFilter = ColorFilter.tint(SDKTheme.colors.iconColor),
                contentDescription = "",
            )
        }
    }
}

@Composable
@Preview
internal fun PreviewLightToolbar() {
    SDKTheme() {
        TopBar("Payment Methods")
    }
}

@Composable
@Preview
internal fun PreviewDarkToolbar() {
    SDKTheme() {
        TopBar(
            title = "Payment Methods"
        )
    }
}

