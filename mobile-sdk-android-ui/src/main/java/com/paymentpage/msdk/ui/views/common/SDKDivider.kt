package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

@Composable
internal fun SDKDivider(
    color: Color = Color.White.copy(alpha = 0.1f),
    isDashed: Boolean = false
) {
    if (!isDashed)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = color)
        )
    else {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 10f)
        Canvas(Modifier.fillMaxWidth().height(1.dp)) {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        }
    }

}