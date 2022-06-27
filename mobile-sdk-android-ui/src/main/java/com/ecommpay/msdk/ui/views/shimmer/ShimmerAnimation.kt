package com.ecommpay.msdk.ui.views.shimmer

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.theme.BackgroundLightShimmerItem

@Composable
fun ShimmerAnimation(
    itemHeight: Dp,
    itemWidth: Dp? = null,
    padding: Dp = 0.dp,
    borderRadius: Dp = 0.dp
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val itemWidthPx = with(LocalDensity.current) {
            (maxWidth - (padding * 2)).toPx()
        }
        val itemHeightPx = with(LocalDensity.current) { (itemHeight - padding).toPx() }

        val gradientWidth = 250f

        val infiniteTransition = rememberInfiniteTransition()
        val xShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = itemWidthPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 500
                ),
                repeatMode = RepeatMode.Restart
            ))

        val yShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = itemHeightPx + gradientWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 500
                ),
                repeatMode = RepeatMode.Restart
            ))

        Column(content = {
            SDKShimmerItem(
                colors = listOf(
                    BackgroundLightShimmerItem.copy(alpha = 1f),
                    BackgroundLightShimmerItem.copy(alpha = 0f),
                    BackgroundLightShimmerItem.copy(alpha = 1f),
                ),
                xShimmer = xShimmer.value,
                yShimmer = yShimmer.value,
                itemHeight = itemHeight,
                itemWidth = itemWidth,
                gradientWidth = gradientWidth,
                padding = padding,
                borderRadius = borderRadius)
        })
    }
}