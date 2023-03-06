package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal class Colors(
    isDarkTheme: Boolean,
    //Brand color
    primary: Color,
    background: Color,
    containerRed: Color,
    red: Color,
    containerGreen: Color,
    green: Color,
    mediumGrey: Color,
    highlight: Color,
    inputField: Color,
    grey: Color,
    neutral: Color,
    link: Color,
    container: Color,
    accent: Color,
) {
    var isDarkTheme by mutableStateOf(isDarkTheme)
        private set
    var primary by mutableStateOf(primary)
        private set
    var background by mutableStateOf(background)
        private set
    var containerRed by mutableStateOf(containerRed)
        private set
    var red by mutableStateOf(red)
        private set
    var containerGreen by mutableStateOf(containerGreen)
        private set
    var green by mutableStateOf(green)
        private set
    var highlight by mutableStateOf(highlight)
        private set
    var inputField by mutableStateOf(inputField)
        private set
    var grey by mutableStateOf(grey)
        private set
    var mediumGrey by mutableStateOf(mediumGrey)
        private set
    var neutral by mutableStateOf(neutral)
        private set
    var link by mutableStateOf(link)
        private set
    var container by mutableStateOf(container)
        private set
    var accent by mutableStateOf(accent)
        private set
}

//Light theme
private val lightThemePrimary = Color(0xFF00579E)
private val lightThemeBackground = Color(0xFFFFFFFF)
private val lightThemeContainerRed = Color(0xFFF8EAEA)
private val lightThemeRed = Color(0xFFC03230)
private val lightThemeContainerGreen = Color(0xFFEBFBEE)
private val lightThemeGreen = Color(0xFF00CC24)
private val lightThemeHighlight = Color(0xFFEBEBEE)
private val lightThemeInputField = Color(0xFFF6F7F9)
private val lightThemeGrey = Color(0xFF666666)
private val lightThemeMediumGrey = Color(0xFFBCBDBE)
private val lightThemeNeutral = Color(0xFF000000)
private val lightThemeLink = Color(0xFF00579E)
private val lightThemeContainer = Color(0xFFE0E0E0)
private val lightThemeAccent = Color(0xFFF2F6FA)

//Dark Theme
private val darkThemePrimary = Color(0xFF054BA0)
private val darkThemeBackground = Color(0xFF181826)
private val darkThemeContainerRed = Color(0xFF4F1B21)
private val darkThemeRed = Color(0xFFDB1F35)
private val darkThemeContainerGreen = Color(0xFF003B0A)
private val darkThemeGreen = Color(0xFF00CC24)
private val darkThemeHighlight = Color(0xFF3C3F5C)
private val darkThemeInputField = Color(0xFF34374D)
private val darkThemeGrey = Color(0xFFA3A3A3)
private val darkThemeMediumGrey = Color(0xFF666980)
private val darkThemeNeutral = Color(0xFFFFFFFF)
private val darkThemeLink = Color(0xFF4299FF)
private val darkThemeContainer = Color(0xFF27293D)
private val darkThemeAccent = Color(0xFF323757)

internal fun lightColors(primaryColor: Color? = null): Colors = Colors(
    isDarkTheme = false,
    primary = primaryColor ?: lightThemePrimary,
    background = lightThemeBackground,
    containerRed = lightThemeContainerRed,
    red = lightThemeRed,
    containerGreen = lightThemeContainerGreen,
    green = lightThemeGreen,
    highlight = lightThemeHighlight,
    inputField = lightThemeInputField,
    grey = lightThemeGrey,
    mediumGrey = lightThemeMediumGrey,
    neutral = lightThemeNeutral,
    link = lightThemeLink,
    container = lightThemeContainer,
    accent = lightThemeAccent
)

internal fun darkColors(primaryColor: Color? = null): Colors = Colors(
    isDarkTheme = true,
    primary = primaryColor ?: darkThemePrimary,
    background = darkThemeBackground,
    containerRed = darkThemeContainerRed,
    red = darkThemeRed,
    containerGreen = darkThemeContainerGreen,
    green = darkThemeGreen,
    highlight = darkThemeHighlight,
    inputField = darkThemeInputField,
    grey = darkThemeGrey,
    mediumGrey = darkThemeMediumGrey,
    neutral = darkThemeNeutral,
    link = darkThemeLink,
    container = darkThemeContainer,
    accent = darkThemeAccent
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