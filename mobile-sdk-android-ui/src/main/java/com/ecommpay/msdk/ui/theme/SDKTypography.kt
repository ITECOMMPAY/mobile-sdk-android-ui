package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal class SDKTypography(
    s12Light: TextStyle,
    s12Normal: TextStyle,
    s14Light: TextStyle,
    s14Normal: TextStyle,
    s14SemiBold: TextStyle,
    s16Normal: TextStyle,
    s16Bold: TextStyle,
    s18Bold: TextStyle,
    s22Bold: TextStyle,
    s24Bold: TextStyle,
    s28Bold: TextStyle,
    isLight: Boolean
) {
    var s12Light by mutableStateOf(s12Light)
        private set
    var s12Normal by mutableStateOf(s12Normal)
        private set
    var s14Light by mutableStateOf(s14Light)
        private set
    var s14Normal by mutableStateOf(s14Normal)
        private set
    var s14SemiBold by mutableStateOf(s14SemiBold)
        private set
    var s16Normal by mutableStateOf(s16Normal)
        private set
    var s16Bold by mutableStateOf(s16Bold)
        private set
    var s18Bold by mutableStateOf(s18Bold)
        private set
    var s22Bold by mutableStateOf(s22Bold)
        private set
    var s24Bold by mutableStateOf(s24Bold)
        private set
    var s28Bold by mutableStateOf(s28Bold)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        s12Light: TextStyle = this.s12Light,
        s12Normal: TextStyle = this.s12Normal,
        s14Light: TextStyle = this.s14Light,
        s14Normal: TextStyle = this.s14Normal,
        s14SemiBold: TextStyle = this.s14SemiBold,
        s16Normal: TextStyle = this.s16Normal,
        s16Bold: TextStyle = this.s16Bold,
        s18Bold: TextStyle = this.s18Bold,
        s22Bold: TextStyle = this.s22Bold,
        s24Bold: TextStyle = this.s24Bold,
        s28Bold: TextStyle = this.s28Bold,
        isLight: Boolean = this.isLight
    ): SDKTypography = SDKTypography(
        s12Light = s12Light,
        s12Normal = s12Normal,
        s14Light = s14Light,
        s14Normal = s14Normal,
        s14SemiBold = s14SemiBold,
        s16Normal = s16Normal,
        s16Bold = s16Bold,
        s18Bold = s18Bold,
        s22Bold = s22Bold,
        s24Bold = s24Bold,
        s28Bold = s28Bold,
        isLight = isLight
    )

    fun updateTypographyFrom(other: SDKTypography) {
        s12Light = other.s12Light
        s12Normal = other.s12Normal
        s14Light = other.s14Light
        s14Normal = other.s14Normal
        s14SemiBold = other.s14SemiBold
        s16Normal = other.s16Normal
        s16Bold = other.s16Bold
        s18Bold = other.s18Bold
        s22Bold = other.s22Bold
        s24Bold = other.s24Bold
        s28Bold = other.s28Bold
    }
}

//Default light typography
private val typographyLightS12Light = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.Black,
    fontSize = 12.sp)
private val typographyLightS12Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 12.sp
)
private val typographyLightS14Light: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14SemiBold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS16Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 16.sp
)
private val typographyLightS16Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 16.sp
)
private val typographyLightS18Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 18.sp
)
private val typographyLightS22Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 22.sp
)
private val typographyLightS24Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 24.sp
)
private val typographyLightS28Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 28.sp,
)
//Default dark typography
private val typographyDarkS12Light = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 12.sp)
private val typographyDarkS12Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 12.sp
)
private val typographyDarkS14Light: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14SemiBold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS16Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 16.sp
)
private val typographyDarkS16Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 16.sp
)
private val typographyDarkS18Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 18.sp
)
private val typographyDarkS22Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 22.sp
)
private val typographyDarkS24Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 24.sp
)
private val typographyDarkS28Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 28.sp,
)
internal fun lightTypography(
    s12Light: TextStyle = typographyLightS12Light,
    s12Normal: TextStyle = typographyLightS12Normal,
    s14Light: TextStyle = typographyLightS14Light,
    s14Normal: TextStyle = typographyLightS14Normal,
    s14SemiBold: TextStyle = typographyLightS14SemiBold,
    s16Normal: TextStyle = typographyLightS16Normal,
    s16Bold: TextStyle = typographyLightS16Bold,
    s18Bold: TextStyle = typographyLightS18Bold,
    s22Bold: TextStyle = typographyLightS22Bold,
    s24Bold: TextStyle = typographyLightS24Bold,
    s28Bold: TextStyle = typographyLightS28Bold
): SDKTypography = SDKTypography(
    s12Light = s12Light,
    s12Normal = s12Normal,
    s14Light = s14Light,
    s14Normal = s14Normal,
    s14SemiBold = s14SemiBold,
    s16Normal = s16Normal,
    s16Bold = s16Bold,
    s18Bold = s18Bold,
    s22Bold = s22Bold,
    s24Bold = s24Bold,
    s28Bold = s28Bold,
    isLight = true
)

internal fun darkTypography(
    s12Light: TextStyle = typographyDarkS12Light,
    s12Normal: TextStyle = typographyDarkS12Normal,
    s14Light: TextStyle = typographyDarkS14Light,
    s14Normal: TextStyle = typographyDarkS14Normal,
    s14SemiBold: TextStyle = typographyDarkS14SemiBold,
    s16Normal: TextStyle = typographyDarkS16Normal,
    s16Bold: TextStyle = typographyDarkS16Bold,
    s18Bold: TextStyle = typographyDarkS18Bold,
    s22Bold: TextStyle = typographyDarkS22Bold,
    s24Bold: TextStyle = typographyDarkS24Bold,
    s28Bold: TextStyle = typographyDarkS28Bold
): SDKTypography = SDKTypography(
    s12Light = s12Light,
    s12Normal = s12Normal,
    s14Light = s14Light,
    s14Normal = s14Normal,
    s14SemiBold = s14SemiBold,
    s16Normal = s16Normal,
    s16Bold = s16Bold,
    s18Bold = s18Bold,
    s22Bold = s22Bold,
    s24Bold = s24Bold,
    s28Bold = s28Bold,
    isLight = false
)

internal val LocalTypography = staticCompositionLocalOf { lightTypography() }