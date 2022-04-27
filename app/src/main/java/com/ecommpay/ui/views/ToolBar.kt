package com.ecommpay.ui.views

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun ToolBar(arrowIcon: ImageVector? = Icons.Filled.ArrowBack, listener: () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(text = "")
        },
        navigationIcon = {
            IconButton(onClick = listener) {
                arrowIcon?.let {
                    Icon(it, "")
                }
            }
        },
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        elevation = 12.dp
    )
}