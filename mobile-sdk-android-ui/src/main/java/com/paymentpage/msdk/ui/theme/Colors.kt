package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal class Colors(
    isDarkTheme: Boolean,
    /**Brand color #1*/
    primary: Color,
    /**Brand color #2*/
    secondary: Color,
    textPrimary: Color,
    textPrimaryInverted: Color,
    background: Color,
    paymentIcon: Color,
    cardBackground: Color,
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
    var secondary by mutableStateOf(secondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textPrimaryInverted by mutableStateOf(textPrimaryInverted)
        private set
    var background by mutableStateOf(background)
        private set
    var paymentIcon by mutableStateOf(paymentIcon)
        private set
    var cardBackground by mutableStateOf(cardBackground)
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


interface SDKColorButton {
    @Composable
    fun text(): State<Color>
    @Composable
    fun arrow(): State<Color>
    @Composable
    fun background(): State<Color>
    @Composable
    fun circleBackground(): State<Color>
    @Composable
    fun card(): State<Color>
}

interface SDKColorInput {
    @Composable
    fun defaultBackground(): State<Color>
    @Composable
    fun errorBackground(): State<Color>
    @Composable
    fun focusedBackground(): State<Color>
    @Composable
    fun disabledBackground(): State<Color>
    @Composable
    fun focusedBorder(): State<Color>
    @Composable
    fun errorBorder(): State<Color>
    @Composable
    fun textPrimary(): State<Color>
    @Composable
    fun textAdditional(): State<Color>
}


//Light theme
private val lightThemePrimary = Color(0xFF4B007C)
private val lightThemeSecondary = Color(0xFFCAB2FF)
private val lightThemeTextPrimary = Color(0xFF000000)
private val lightThemeBackground = Color(0xFFF0F1F3)
private val lightThemeCardBackground = Color(0xFFFFFFFF)
private val lightThemeContainerRed = Color(0xFFF8EAEA)
private val lightThemeRed = Color(0xFFC03230)
private val lightThemeContainerGreen = Color(0xFFEBFBEE)
private val lightThemeGreen = Color(0xFF00CC24)
private val lightThemeHighlight = Color(0xFFEBEBEE)
private val lightThemeInputField = Color(0xFFF6F7F9)
private val lightThemeGrey = Color(0xFF666666)
private val lightThemeMediumGrey = Color(0xFFBCBDBE)
private val lightThemeNeutral = Color(0xFF000000)
private val lightThemeContainer = Color(0xFFFFFFFF)
private val lightThemeAccent = Color(0xFFF2F6FA)

// Checkbox
internal val selectedCheckBox = Color(0xFF4B007C)
internal val unselectedCheckBox = Color(0xFFF7F7F7)

// Button
internal val lightButtonText = Color(0xFF000000)
internal val darkButtonText = Color(0xFFFFFFFF)
internal val lightButtonArrow = Color(0xFFFFFFFF)
internal val darkButtonArrow = Color(0xFF000000)
internal val lightButtonCard = Color(0xFFFFFFFF)
internal val darkButtonCard = Color(0xFF000000)

// Input
internal val lightInputNeutral = Color(0xFFEEEEEE)
internal val darkInputNeutral = Color(0xFF3E3E3E)
internal val lightInputDisabled = Color(0x0A000000)
internal val darkInputDisabled = Color(0x0AFFFFFF)
internal val lightInputErrorBackground = Color(0xFFFFE4E1)
internal val darkInputErrorBackground = Color(0xFF3B0306)


internal val lightInputErrorBorder = Color(0xFFCF0022)
internal val darkInputErrorBorder = Color(0xFFFE5555)

internal val lightInputTextPrimary = Color(0xFF000000)
internal val darkInputTextPrimary = Color(0xFFF5F5F5)
internal val lightInputTextAdditional = Color(0xFF666666)
internal val darkInputTextAdditional = Color(0xFFBFBFBF)


//Dark Theme
private val darkThemePrimary = Color(0xFF4B007C)
private val darkThemeSecondary = Color(0xFFCAB2FF)
private val darkThemeTextPrimary = Color(0xFFFFFFFF)
private val darkThemeBackground = Color(0xFF121212)
private val darkThemeCardBackground = Color(0xFF212121)
private val darkThemeContainerRed = Color(0xFF4F1B21)
private val darkThemeRed = Color(0xFFDB1F35)
private val darkThemeContainerGreen = Color(0xFF003B0A)
private val darkThemeGreen = Color(0xFF00CC24)
private val darkThemeHighlight = Color(0xFF3C3F5C)
private val darkThemeInputField = Color(0xFF34374D)
private val darkThemeGrey = Color(0xFFA3A3A3)
private val darkThemeMediumGrey = Color(0xFF666980)
private val darkThemeNeutral = Color(0xFFFFFFFF)
private val darkThemeContainer = Color(0xFF27293D)
private val darkThemeAccent = Color(0xFF323757)

internal fun lightColors(
    primaryColor: Color? = null,
    secondaryColor: Color? = null,
): Colors = Colors(
    isDarkTheme = false,
    primary = primaryColor ?: lightThemePrimary,
    secondary = secondaryColor ?: lightThemeSecondary,
    textPrimary = lightThemeTextPrimary,
    textPrimaryInverted = darkThemeTextPrimary,
    background = lightThemeBackground,
    paymentIcon = primaryColor ?: lightThemePrimary,
    cardBackground = lightThemeCardBackground,
    containerRed = lightThemeContainerRed,
    red = lightThemeRed,
    containerGreen = lightThemeContainerGreen,
    green = lightThemeGreen,
    highlight = lightThemeHighlight,
    inputField = lightThemeInputField,
    grey = lightThemeGrey,
    mediumGrey = lightThemeMediumGrey,
    neutral = lightThemeNeutral,
    link = primaryColor ?: lightThemePrimary,
    container = lightThemeContainer,
    accent = lightThemeAccent
)

internal fun darkColors(
    primaryColor: Color? = null,
    secondaryColor: Color? = null,
): Colors = Colors(
    isDarkTheme = true,
    primary = primaryColor ?: darkThemePrimary,
    secondary = secondaryColor ?: darkThemeSecondary,
    textPrimary = darkThemeTextPrimary,
    textPrimaryInverted = lightThemeTextPrimary,
    background = darkThemeBackground,
    paymentIcon = secondaryColor ?: darkThemeSecondary,
    cardBackground = darkThemeCardBackground,
    containerRed = darkThemeContainerRed,
    red = darkThemeRed,
    containerGreen = darkThemeContainerGreen,
    green = darkThemeGreen,
    highlight = darkThemeHighlight,
    inputField = darkThemeInputField,
    grey = darkThemeGrey,
    mediumGrey = darkThemeMediumGrey,
    neutral = darkThemeNeutral,
    link = secondaryColor ?: darkThemeSecondary,
    container = darkThemeGrey,
    accent = darkThemeAccent
)

@Composable
internal fun selectColor(lightThemeColor: Color, darkThemeColor: Color) =
    if (LocalDarkThemeSelection.current) darkThemeColor else lightThemeColor

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