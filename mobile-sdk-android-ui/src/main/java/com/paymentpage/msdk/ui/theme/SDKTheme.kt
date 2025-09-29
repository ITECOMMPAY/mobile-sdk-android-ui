package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

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
    isDarkTheme: Boolean = false,
    primaryBrandColor: Color? = null,
    secondaryBrandColor: Color? = null,
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme)
        darkColors(primaryColor = primaryBrandColor, secondaryColor = secondaryBrandColor)
    else
        lightColors(primaryColor = primaryBrandColor, secondaryColor = secondaryBrandColor)

    val typography = if (isDarkTheme)
        darkTypography()
    else
        lightTypography()

    val images = if (isDarkTheme)
        darkImages()
    else
        lightImages()

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides SDKTheme.dimensions,
        LocalTypography provides typography,
        LocalShapes provides SDKTheme.shapes,
        LocalImages provides images
    ) {
        content()
    }
}