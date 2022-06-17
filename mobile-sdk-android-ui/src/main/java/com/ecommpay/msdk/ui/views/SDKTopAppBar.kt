package com.ecommpay.msdk.ui.views

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.theme.TopAppBarCloseButton
import java.lang.reflect.Modifier

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SDKTopAppBar(
    title: String,
    arrowIcon: ImageVector? = Icons.Filled.ArrowBack,
    listener: () -> Unit = {},
) {
    //Получаем контроллер системной клавиатуры
    val keyboardController = LocalSoftwareKeyboardController.current
    TopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.h1,
                text = title
            )
        },
        navigationIcon = {
            if (arrowIcon != null) {
                IconButton(
                    onClick = {
                        //Скрываем системную клавиатуру при переходе на предыдщий экран
                        keyboardController?.hide()
                        listener()
                    }) {
                    Icon(
                        arrowIcon,
                        "")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = TopAppBarCloseButton,
        elevation = 0.dp,
        actions = {
            IconButton(
                onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "",
                    tint = TopAppBarCloseButton
                )
            }
        }
    )
}

@Composable
@Preview
fun PreviewLightToolbar() {
    SDKTheme(darkTheme = false) {
        SDKTopAppBar("Payment Methods")
    }
}

@Composable
@Preview
fun PreviewDarkToolbar() {
    SDKTheme(darkTheme = true) {
        SDKTopAppBar("Payment Methods")
    }
}

