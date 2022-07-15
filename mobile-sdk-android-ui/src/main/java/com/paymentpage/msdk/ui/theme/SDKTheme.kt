@file:Suppress("UNUSED_PARAMETER")

package com.paymentpage.msdk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

internal object SDKTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val images: Images
        @Composable
        @ReadOnlyComposable
        get() = LocalImages.current
}

@Composable
internal fun SDKTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (isDark) DarkColorPalette else LightColorPalette
//    val typography = if (isDark) darkTypography() else  lightTypography()
//    val images = if (isDark) darkImages() else lightImages()
    CompositionLocalProvider(
        LocalColors provides lightColors(),
        LocalDimensions provides SDKTheme.dimensions,
        LocalTypography provides lightTypography(),
        LocalShapes provides SDKTheme.shapes,
        LocalImages provides lightImages()
    ) {
        content()
    }
}