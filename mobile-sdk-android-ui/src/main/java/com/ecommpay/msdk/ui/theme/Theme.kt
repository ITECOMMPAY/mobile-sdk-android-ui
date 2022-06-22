package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun SDKTheme(
    colors: SDKColors = SDKTheme.colors,
    typography: SDKTypography = SDKTheme.typography,
    dimensions: SDKDimensions = SDKTheme.dimensions,
    shapes: SDKShapes = SDKTheme.shapes,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
        LocalShapes provides shapes
    ) {
        content()
    }
}