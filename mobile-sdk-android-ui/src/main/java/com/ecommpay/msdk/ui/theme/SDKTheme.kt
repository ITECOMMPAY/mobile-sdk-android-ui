package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object SDKTheme {
    val colors: SDKColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: SDKTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: SDKDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val shapes: SDKShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}