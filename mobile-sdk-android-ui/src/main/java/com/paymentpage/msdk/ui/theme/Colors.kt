@file:Suppress("unused")

package com.paymentpage.msdk.ui.theme

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
    successTextColor: Color,
    errorTextColor: Color,
    backgroundColor: Color,
    panelBackgroundColor: Color,
    panelBackgroundErrorColor: Color,
    borderColor: Color,
    borderErrorColor: Color,
    iconColor: Color,
    footerTextColor: Color,
) {
    var brand by mutableStateOf(brand)
        private set
    var primaryTextColor by mutableStateOf(primaryTextColor)
        private set
    var secondaryTextColor by mutableStateOf(secondaryTextColor)
        private set
    var disabledTextColor by mutableStateOf(disabledTextColor)
        private set
    var successTextColor by mutableStateOf(successTextColor)
        private set
    var errorTextColor by mutableStateOf(errorTextColor)
        private set
    var backgroundColor by mutableStateOf(backgroundColor)
        private set
    var panelBackgroundColor by mutableStateOf(panelBackgroundColor)
        private set
    var borderColor by mutableStateOf(borderColor)
        private set
    var footerTextColor by mutableStateOf(footerTextColor)
        private set
    var panelBackgroundErrorColor by mutableStateOf(panelBackgroundErrorColor)
        private set
    var borderErrorColor by mutableStateOf(borderErrorColor)
        private set
    var iconColor by mutableStateOf(iconColor)
        private set
}

val BrandLight = Color(0xFF00579E)

private val colorBlack = Color(0xFF000000)
private val colorWhite = Color(0xFFFFFFFF)

//Default Light theme
private val lightThemeBrandColor = Color(0xFF00579E)

private val lightThemeLightGrayColor = Color(0xFFF6F7F9)
private val lightThemeMediumGrayColor = Color(0xFFBCBDBE)
private val lightThemeDarkGrayColor = Color(0xFF666666)
private val lightThemeGrayColor = Color(0xFFEBEBEE)

private val lightThemeGreenColor = Color(0xFF00CC24)
private val lightThemeRedColor = Color(0xFFC03230)
private val lightThemeLightRedColor = Color(0xFFF8EAEA)


internal fun lightColors(brandColor: Color? = lightThemeBrandColor): Colors = Colors(
    brand = brandColor ?: lightThemeBrandColor,
    primaryTextColor = colorBlack,
    secondaryTextColor = lightThemeDarkGrayColor,
    disabledTextColor = lightThemeMediumGrayColor,
    successTextColor = lightThemeGreenColor,
    errorTextColor = lightThemeRedColor,
    backgroundColor = colorWhite,
    iconColor = colorBlack,
    panelBackgroundColor = lightThemeLightGrayColor,
    borderColor = lightThemeGrayColor,
    footerTextColor = lightThemeMediumGrayColor,
    panelBackgroundErrorColor = lightThemeLightRedColor,
    borderErrorColor = lightThemeRedColor
)


internal val LocalColors = staticCompositionLocalOf { lightColors() }

object HexToJetpackColor {
    fun getColor(colorString: String?): Color? {
        if (colorString.isNullOrEmpty())
            return null
        return try {
            Color(android.graphics.Color.parseColor(colorString))
        } catch (e: Exception) {
            null
        }
    }
}