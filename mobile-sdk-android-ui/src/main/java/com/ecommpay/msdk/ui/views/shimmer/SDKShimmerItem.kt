package com.ecommpay.msdk.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.BackgroundLightShimmerItem
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun SDKShimmerItem(
    colors: List<Color>,
    xShimmer: Float,
    yShimmer: Float,
    itemHeight: Dp,
    itemWidth: Dp? = null,
    gradientWidth: Float,
    padding: Dp,
    borderRadius: Dp = 0.dp,
) {
    val brush = Brush.linearGradient(
        0.0f to SDKTheme.colors.backgroundShimmerItem,
        0.5f to SDKTheme.colors.backgroundPaymentMethods,
        1.0f to SDKTheme.colors.backgroundShimmerItem,
        start = Offset(x = xShimmer - gradientWidth, y = yShimmer - gradientWidth),
        end = Offset(x = xShimmer, y = yShimmer)
    )
    Column(
        modifier = Modifier
            .padding(padding)
    ) {
        Surface(
            shape = RoundedCornerShape(borderRadius)
        ) {
            Spacer(
                modifier = if (itemWidth != null) {
                    Modifier
                        .width(itemWidth)
                } else {
                    Modifier
                        .fillMaxWidth()
                }
                    .height(itemHeight)
                    .background(brush)
            )
        }
    }
}