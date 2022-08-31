package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
internal fun AdditionalFieldsButton(listener: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClick = listener) {
        Text(text = "Additional fields", color = Color.White, fontSize = 18.sp)
    }
}