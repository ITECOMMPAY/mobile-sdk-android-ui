package com.ecommpay.msdk.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = Purple700,
    secondary = SecondaryDark,
    surface = SurfaceDark,
    onSurface = SurfaceAlpha
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = Purple700,
    secondary = SecondaryLight,
    surface = SurfaceLight,
    onSurface = SurfaceAlpha

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SDKTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = SDKTypography(darkTheme)

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}