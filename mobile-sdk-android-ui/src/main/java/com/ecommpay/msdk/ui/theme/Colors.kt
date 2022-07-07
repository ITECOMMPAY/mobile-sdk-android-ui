@file:Suppress("unused")

package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal class Colors(
    //Brand color
    brand: Color,
    primaryTextColor: Color,
    secondaryTextColor: Color,
    disabledTextColor: Color,
    backgroundColor: Color,
    lightGray: Color,
    mediumGray: Color,
    gray: Color,
    iconColor: Color,
) {
    var brand by mutableStateOf(brand)
        private set
    var primaryTextColor by mutableStateOf(primaryTextColor)
        private set
    var secondaryTextColor by mutableStateOf(secondaryTextColor)
        private set
    var disabledTextColor by mutableStateOf(disabledTextColor)
        private set
    var lightGray by mutableStateOf(lightGray)
        private set
    var backgroundColor by mutableStateOf(backgroundColor)
        private set
    var mediumGray by mutableStateOf(mediumGray)
        private set
    var gray by mutableStateOf(gray)
        private set
    var iconColor by mutableStateOf(iconColor)
        private set
}

val BrandLight = Color(0xFF00579E)

private val colorBlack = Color(0xFF000000)
private val colorWhite = Color(0xFFFFFFFF)

//Default Light theme
private val lightThemeBrandColor = Color(0xFF00579E)

//private val colorLightTopBarCloseButton = Color(0xFF00579E)
private val lightThemeLightGrayColor = Color(0xFFF6F7F9)
private val lightThemeMediumGrayColor = Color(0xFFBCBDBE)
private val lightThemeDarkGrayColor = Color(0xFF666666)
private val lightThemeGrayColor = Color(0xFFEBEBEE)

//private val colorLightBackgroundExpandedPaymentDetails = Color(0xFFF6F7F9)


internal fun lightColors(brand: Color = lightThemeBrandColor): Colors = Colors(
    brand = brand,
    primaryTextColor = colorBlack,
    secondaryTextColor = lightThemeDarkGrayColor,
    disabledTextColor = lightThemeMediumGrayColor,
    lightGray = lightThemeLightGrayColor,
    backgroundColor = colorWhite,
    mediumGray = lightThemeMediumGrayColor,
    gray = lightThemeGrayColor,
    iconColor = colorBlack,
)


internal val LocalColors = staticCompositionLocalOf { lightColors() }