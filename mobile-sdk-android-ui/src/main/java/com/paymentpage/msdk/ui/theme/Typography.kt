@file:Suppress("unused")

package com.paymentpage.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.paymentpage.msdk.ui.R

internal class Typography(
    s12Light: TextStyle,
    s12Normal: TextStyle,
    s14Light: TextStyle,
    s14Normal: TextStyle,
    s14Medium: TextStyle,
    s14SemiBold: TextStyle,
    s14Bold: TextStyle,
    s15Normal: TextStyle,
    s15Medium: TextStyle,
    s16Normal: TextStyle,
    s16Bold: TextStyle,
    s18Normal: TextStyle,
    s18Bold: TextStyle,
    s22Bold: TextStyle,
    s24Bold: TextStyle,
    s28Bold: TextStyle,
    s36Medium: TextStyle,
    s36Bold: TextStyle,
) {
    var s12Light by mutableStateOf(s12Light)
        private set
    var s12Normal by mutableStateOf(s12Normal)
        private set
    var s14Light by mutableStateOf(s14Light)
        private set
    var s14Normal by mutableStateOf(s14Normal)
        private set
    var s14Medium by mutableStateOf(s14Medium)
        private set
    var s14SemiBold by mutableStateOf(s14SemiBold)
        private set
    var s14Bold by mutableStateOf(s14Bold)
        private set
    var s15Normal by mutableStateOf(s15Normal)
        private set
    var s15Medium by mutableStateOf(s15Medium)
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
    var s36Medium by mutableStateOf(s36Medium)
        private set
    var s36Bold by mutableStateOf(s36Bold)
        private set
}

val InterFamily = FontFamily(
    Font(
        resId = R.font.inter_18pt_thin,
        weight = FontWeight.W100
    ),
    Font(
        resId = R.font.inter_18pt_thin_italic,
        weight = FontWeight.W100,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_extra_light,
        weight = FontWeight.W200
    ),
    Font(
        resId = R.font.inter_18pt_extra_light_italic,
        weight = FontWeight.W200,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_light,
        weight = FontWeight.W300
    ),
    Font(
        resId = R.font.inter_18pt_light_italic,
        weight = FontWeight.W300,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_regular,
        weight = FontWeight.W400
    ),
    Font(
        resId = R.font.inter_18pt_medium,
        weight = FontWeight.W500
    ),
    Font(
        resId = R.font.inter_18pt_medium_italic,
        weight = FontWeight.W500,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_semi_bold,
        weight = FontWeight.W600
    ),
    Font(
        resId = R.font.inter_18pt_semi_bold_italic,
        weight = FontWeight.W600,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_bold,
        weight = FontWeight.W700
    ),
    Font(
        resId = R.font.inter_18pt_bold_italic,
        weight = FontWeight.W700,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_extra_bold,
        weight = FontWeight.W800
    ),
    Font(
        resId = R.font.inter_18pt_extra_bold_italic,
        weight = FontWeight.W800,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.inter_18pt_black,
        weight = FontWeight.W900
    ),
    Font(
        resId = R.font.inter_18pt_black_italic,
        weight = FontWeight.W900,
        style = FontStyle.Italic,
    ),
)

val SohneBreitFamily = FontFamily(
    Font(
        resId = R.font.sohne_breit_extraleicht,
        weight = FontWeight.W200
    ),
    Font(
        resId = R.font.sohne_breit_extraleicht_kursiv,
        weight = FontWeight.W200,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_leicht,
        weight = FontWeight.W300
    ),
    Font(
        resId = R.font.sohne_breit_leicht_kursiv,
        weight = FontWeight.W300,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_buch,
        weight = FontWeight.W400
    ),
    Font(
        resId = R.font.sohne_breit_buch_kursiv,
        weight = FontWeight.W400,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_kraftig,
        weight = FontWeight.W500
    ),
    Font(
        resId = R.font.sohne_breit_kraftig_kursiv,
        weight = FontWeight.W500,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_halbfett,
        weight = FontWeight.W600
    ),
    Font(
        resId = R.font.sohne_breit_halbfett_kursiv,
        weight = FontWeight.W600,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.soohne_breit_dreiviertelfett,
        weight = FontWeight.W700
    ),
    Font(
        resId = R.font.sohne_breit_dreiviertelfett_kursiv,
        weight = FontWeight.W700,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_fett,
        weight = FontWeight.W800
    ),
    Font(
        resId = R.font.sohne_breit_fett_kursiv,
        weight = FontWeight.W800,
        style = FontStyle.Italic,
    ),
    Font(
        resId = R.font.sohne_breit_extrafett,
        weight = FontWeight.W900
    ),
    Font(
        resId = R.font.sohne_breit_extrafett_kursiv,
        weight = FontWeight.W900,
        style = FontStyle.Italic,
    ),
)

//Light typography
private val typographyLightS12Light = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Light,
    color = Color.Black,
    fontSize = 12.sp
)
private val typographyLightS12Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 12.sp
)
private val typographyLightS14Light: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Light,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14SemiBold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS14Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 14.sp
)
private val typographyLightS15Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 15.sp
)
private val typographyLightS15Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.Black,
    fontSize = 15.sp
)
private val typographyLightS16Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 16.sp
)
private val typographyLightS16Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 16.sp
)
private val typographyLightS18Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 18.sp
)
private val typographyLightS18Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 18.sp
)
private val typographyLightS22Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 22.sp
)
private val typographyLightS24Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 24.sp
)

private val typographyLightS28Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 28.sp,
)
private val typographyLightS36Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.Black,
    fontSize = 36.sp,
)
private val typographyLightS36Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 36.sp,
)

//Dark typography
private val typographyDarkS12Light = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 12.sp
)
private val typographyDarkS12Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 12.sp
)
private val typographyDarkS14Light: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Light,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14SemiBold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.SemiBold,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS14Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 14.sp
)
private val typographyDarkS15Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 15.sp
)
private val typographyDarkS15Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.White,
    fontSize = 15.sp
)
private val typographyDarkS16Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 16.sp
)
private val typographyDarkS16Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 16.sp
)
private val typographyDarkS18Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 18.sp
)
private val typographyDarkS18Normal: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontSize = 18.sp
)
private val typographyDarkS22Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 22.sp
)
private val typographyDarkS24Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 24.sp
)
private val typographyDarkS28Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 28.sp,
)
private val typographyDarkS36Medium: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Medium,
    color = Color.White,
    fontSize = 36.sp,
)
private val typographyDarkS36Bold: TextStyle = TextStyle(
    fontFamily = InterFamily,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontSize = 36.sp,
)

internal fun lightTypography(): Typography = Typography(
    s12Light = typographyLightS12Light,
    s12Normal = typographyLightS12Normal,
    s14Light = typographyLightS14Light,
    s14Normal = typographyLightS14Normal,
    s14Medium = typographyLightS14Medium,
    s14SemiBold = typographyLightS14SemiBold,
    s14Bold = typographyLightS14Bold,
    s15Normal = typographyLightS15Normal,
    s15Medium = typographyLightS15Medium,
    s16Normal = typographyLightS16Normal,
    s16Bold = typographyLightS16Bold,
    s18Bold = typographyLightS18Bold,
    s22Bold = typographyLightS22Bold,
    s24Bold = typographyLightS24Bold,
    s28Bold = typographyLightS28Bold,
    s18Normal = typographyLightS18Normal,
    s36Medium = typographyLightS36Medium,
    s36Bold = typographyLightS36Bold,
)

internal fun darkTypography(): Typography = Typography(
    s12Light = typographyDarkS12Light,
    s12Normal = typographyDarkS12Normal,
    s14Light = typographyDarkS14Light,
    s14Normal = typographyDarkS14Normal,
    s14Medium = typographyDarkS14Medium,
    s14SemiBold = typographyDarkS14SemiBold,
    s14Bold = typographyDarkS14Bold,
    s15Normal = typographyDarkS15Normal,
    s15Medium = typographyDarkS15Medium,
    s16Normal = typographyDarkS16Normal,
    s16Bold = typographyDarkS16Bold,
    s18Bold = typographyDarkS18Bold,
    s18Normal = typographyDarkS18Normal,
    s22Bold = typographyDarkS22Bold,
    s24Bold = typographyDarkS24Bold,
    s28Bold = typographyDarkS28Bold,
    s36Medium = typographyDarkS36Medium,
    s36Bold = typographyDarkS36Bold,
)

internal val LocalTypography = staticCompositionLocalOf { lightTypography() }