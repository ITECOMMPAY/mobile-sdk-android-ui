package com.ecommpay.msdk.ui.views

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.theme.SDKTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SDKToolBar(arrowIcon: ImageVector? = Icons.Filled.ArrowBack, listener: () -> Unit = {}) {
    //Получаем контроллер системной клавиатуры
    val keyboardController = LocalSoftwareKeyboardController.current
    TopAppBar(
        title = {
            Text(
                style = SDKTypography(true).body1,
                text = "123123f")
        },
        navigationIcon = {
            IconButton(onClick = {
                //Скрываем системную клавиатуру при переходе на предыдщий экран
                keyboardController?.hide()
                listener()
            }) {
                arrowIcon?.let {
                    Icon(it, "")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 0.dp
    )
}

@Composable
@Preview
fun PreviewLightToolbar() {
    SDKTheme(darkTheme = false) {
        SDKToolBar()
    }
}

@Composable
@Preview
fun PreviewDarkToolbar() {
    SDKTheme(darkTheme = true) {
        SDKToolBar()
    }
}

