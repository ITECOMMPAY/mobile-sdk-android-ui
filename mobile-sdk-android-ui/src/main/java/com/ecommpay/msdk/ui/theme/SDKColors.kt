package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class SDKColors(
    //Brand color
    brand: Color,
    //TopBar
    backgroundTopBar: Color,
    topBarCloseButton: Color,
    //Payment Methods Screen
    backgroundPaymentMethods: Color,
    //Shimmer Screen
    backgroundShimmerItem: Color,
    //
    isLight: Boolean,
) {
    var brand by mutableStateOf(brand)
        private set
    var backgroundTopBar by mutableStateOf(backgroundTopBar)
        private set
    var topBarCloseButton by mutableStateOf(topBarCloseButton)
        private set
    var backgroundShimmerItem by mutableStateOf(backgroundShimmerItem)
        private set
    var backgroundPaymentMethods by mutableStateOf(backgroundPaymentMethods)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        brand: Color = this.brand,
        backgroundTopBar: Color = this.backgroundTopBar,
        topBarCloseButton: Color = this.topBarCloseButton,
        backgroundShimmerItem: Color = this.backgroundShimmerItem,
        backgroundPaymentMethods: Color = this.backgroundPaymentMethods,
        isLight: Boolean = this.isLight,
    ): SDKColors = SDKColors(
        brand,
        backgroundTopBar,
        topBarCloseButton,
        backgroundShimmerItem,
        backgroundPaymentMethods,
        isLight
    )

    fun updateColorsFrom(other: SDKColors) {
        brand = other.brand
        backgroundTopBar = other.backgroundTopBar
        topBarCloseButton = other.topBarCloseButton
        backgroundShimmerItem = other.backgroundShimmerItem
        backgroundPaymentMethods = other.backgroundPaymentMethods
    }
}
//Default Light theme
private val colorLightBrand = Color(0xFF00579E)
private val colorLightBackgroundTopBar = Color(0xFFFFFFFF)
private val colorLightTopBarCloseButton = Color(0xFF00579E)
private val colorLightBackgroundShimmerItem = Color(0xFFF6F7F9)
private val colorLightBackgroundPaymentMethods = Color(0xFFFFFFFF)
//Default Dark theme
private val colorDarkBrand = Color(0xFF0065B8)
private val colorDarkBackgroundTopBar = Color(0xFF2D2B2B)
private val colorDarkTopBarCloseButton = Color(0xFF0065B8)
private val colorDarkBackgroundShimmerItem = Color(0xFF2D2B2B)
private val colorDarkBackgroundPaymentMethods = Color(0xFF141414)

fun lightColors(
    brand: Color? = null,
    backgroundTopBar: Color = colorLightBackgroundTopBar,
    topBarCloseButton: Color = brand ?: colorLightTopBarCloseButton,
    backgroundShimmerItem: Color = colorLightBackgroundShimmerItem,
    backgroundPaymentMethods: Color = colorLightBackgroundPaymentMethods,
): SDKColors = SDKColors(
    brand = brand ?: colorLightBrand,
    backgroundTopBar = backgroundTopBar,
    topBarCloseButton = topBarCloseButton,
    backgroundShimmerItem = backgroundShimmerItem,
    backgroundPaymentMethods = backgroundPaymentMethods,
    isLight = true
)

fun darkColors(
    brand: Color? = null,
    backgroundTopBar: Color = colorDarkBackgroundTopBar,
    topBarCloseButton: Color = brand ?: colorDarkTopBarCloseButton,
    backgroundShimmerItem: Color = colorDarkBackgroundShimmerItem,
    backgroundPaymentMethods: Color = colorDarkBackgroundPaymentMethods,
): SDKColors = SDKColors(
    brand = brand ?: colorDarkBrand,
    backgroundTopBar = backgroundTopBar,
    topBarCloseButton = topBarCloseButton,
    backgroundShimmerItem = backgroundShimmerItem,
    backgroundPaymentMethods = backgroundPaymentMethods,
    isLight = false
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }