@file:Suppress("unused")

package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal class Typography(
    s12Light: TextStyle,
    s12Normal: TextStyle,
    s14Light: TextStyle,
    s14Normal: TextStyle,
    s14SemiBold: TextStyle,
    s14Bold: TextStyle,
    s16Normal: TextStyle,
    s16Bold: TextStyle,
    s18Normal: TextStyle,
    s18Bold: TextStyle,
    s22Bold: TextStyle,
    s24Bold: TextStyle,
    s28Bold: TextStyle,
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
    var s14Bold by mutableStateOf(s14Bold)
        private set
    var s16Normal by mutableStateOf(s16Normal)
        private set
    var s16Bold by mutableStateOf(s16Bold)
        private set
    var s18Bold by mutableStateOf(s18Bold)
        private set
    var s18Normal by mutableStateOf(s18Normal)
        private set
    var s22Bold by mutableStateOf(s22Bold)
        private set
    var s24Bold by mutableStateOf(s24Bold)
        private set
    var s28Bold by mutableStateOf(s28Bold)
        private set
}

//Light typography
private val typographyLightS12Light = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.Black,
    fontSize = 12.sp
)
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
private val typographyLightS14Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
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
private val typographyLightS18Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
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

//Dark typography
private val typographyDarkS12Light = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 12.sp
)
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
private val typographyDarkS14Bold: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Bold,
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
private val typographyDarkS18Normal: TextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
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

internal fun lightTypography(): Typography = Typography(
    s12Light = typographyLightS12Light,
    s12Normal = typographyLightS12Normal,
    s14Light = typographyLightS14Light,
    s14Normal = typographyLightS14Normal,
    s14SemiBold = typographyLightS14SemiBold,
    s14Bold = typographyLightS14Bold,
    s16Normal = typographyLightS16Normal,
    s16Bold = typographyLightS16Bold,
    s18Bold = typographyLightS18Bold,
    s22Bold = typographyLightS22Bold,
    s24Bold = typographyLightS24Bold,
    s28Bold = typographyLightS28Bold,
    s18Normal = typographyLightS18Normal,
)

internal fun darkTypography(): Typography = Typography(
    s12Light = typographyDarkS12Light,
    s12Normal = typographyDarkS12Normal,
    s14Light = typographyDarkS14Light,
    s14Normal = typographyDarkS14Normal,
    s14SemiBold = typographyDarkS14SemiBold,
    s14Bold = typographyDarkS14Bold,
    s16Normal = typographyDarkS16Normal,
    s16Bold = typographyDarkS16Bold,
    s18Bold = typographyDarkS18Bold,
    s18Normal = typographyDarkS18Normal,
    s22Bold = typographyDarkS22Bold,
    s24Bold = typographyDarkS24Bold,
    s28Bold = typographyDarkS28Bold
)

internal val LocalTypography = staticCompositionLocalOf { lightTypography() }