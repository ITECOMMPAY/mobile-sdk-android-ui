package com.ecommpay.msdk.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal class SDKColors(
    //Brand color
    brand: Color,
    //TopBar
    backgroundTopBar: Color,
    topBarCloseButton: Color,
    //Shimmer Screen
    backgroundShimmerItem: Color,
    //Payment Methods Screen
    backgroundExpandedPaymentDetails: Color,
    expandedPaymentDetailsCloseButton: Color,
    backgroundPaymentMethods: Color,
    backgroundPaymentMethodItem: Color,
    borderPaymentMethodItem: Color,
    //Footer content
    footerContent: Color,
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
    var backgroundExpandedPaymentDetails by mutableStateOf(backgroundExpandedPaymentDetails)
        private set
    var expandedPaymentDetailsCloseButton by mutableStateOf(expandedPaymentDetailsCloseButton)
        private set
    var backgroundPaymentMethods by mutableStateOf(backgroundPaymentMethods)
        private set
    var backgroundPaymentMethodItem by mutableStateOf(backgroundPaymentMethodItem)
        private set
    var borderPaymentMethodItem by mutableStateOf(borderPaymentMethodItem)
        private set
    var footerContent by mutableStateOf(footerContent)
        private set
    var isLight by mutableStateOf(isLight)
        internal set

    fun copy(
        brand: Color = this.brand,
        backgroundTopBar: Color = this.backgroundTopBar,
        topBarCloseButton: Color = this.topBarCloseButton,
        backgroundShimmerItem: Color = this.backgroundShimmerItem,
        backgroundExpandedPaymentDetails: Color = this.backgroundExpandedPaymentDetails,
        expandedPaymentDetailsCloseButton: Color = this.expandedPaymentDetailsCloseButton,
        backgroundPaymentMethods: Color = this.backgroundPaymentMethods,
        backgroundPaymentMethodItem: Color = this.backgroundPaymentMethodItem,
        borderPaymentMethodItem: Color = this.borderPaymentMethodItem,
        footerContent: Color = this.footerContent,
        isLight: Boolean = this.isLight,
    ): SDKColors = SDKColors(
        brand,
        backgroundTopBar,
        topBarCloseButton,
        backgroundShimmerItem,
        backgroundExpandedPaymentDetails,
        expandedPaymentDetailsCloseButton,
        backgroundPaymentMethods,
        backgroundPaymentMethodItem,
        borderPaymentMethodItem,
        footerContent,
        isLight
    )

    fun updateColorsFrom(other: SDKColors) {
        brand = other.brand
        backgroundTopBar = other.backgroundTopBar
        topBarCloseButton = other.topBarCloseButton
        backgroundShimmerItem = other.backgroundShimmerItem
        backgroundExpandedPaymentDetails = other.backgroundExpandedPaymentDetails
        expandedPaymentDetailsCloseButton = other.expandedPaymentDetailsCloseButton
        backgroundPaymentMethods = other.backgroundPaymentMethods
        backgroundPaymentMethodItem = other.backgroundPaymentMethodItem
        borderPaymentMethodItem = other.borderPaymentMethodItem
        footerContent = other.footerContent
    }
}
//Default Light theme
private val colorLightBrand = Color(0xFF00579E)
private val colorLightBackgroundTopBar = Color(0xFFFFFFFF)
private val colorLightTopBarCloseButton = Color(0xFF00579E)
private val colorLightBackgroundShimmerItem = Color(0xFFF6F7F9)
private val colorLightBackgroundExpandedPaymentDetails = Color(0xFFF6F7F9)
private val colorLightExpandedPaymentDetailsCloseButton = Color(0xFF000000)
private val colorLightBackgroundPaymentMethods = Color(0xFFFFFFFF)
private val colorLightBackgroundPaymentMethodItem = Color(0xFFFFFFFF)
private val colorLightBorderPaymentMethodItem = Color(0xFFEBEBEE)
private val colorLightFooterContent = Color(0xFFBCBDBE)
//Default Dark theme
private val colorDarkBrand = Color(0xFF0065B8)
private val colorDarkBackgroundTopBar = Color(0xFF141414)
private val colorDarkTopBarCloseButton = Color(0xFF0065B8)
private val colorDarkBackgroundShimmerItem = Color(0xFF2D2B2B)
private val colorDarkBackgroundExpandedPaymentDetails = Color(0xFFF6F7F9)
private val colorDarkExpandedPaymentDetailsCloseButton = Color(0xFFFFFFFF)
private val colorDarkBackgroundPaymentMethods = Color(0xFF141414)
private val colorDarkBackgroundPaymentMethodItem = Color(0xFF141414)
private val colorDarkBorderPaymentMethodItem = Color(0xFFEBEBEE)
private val colorDarkFooterContent = Color(0xFFBCBDBE)

internal fun lightColors(
    brand: Color? = null,
    backgroundTopBar: Color = colorLightBackgroundTopBar,
    topBarCloseButton: Color = brand ?: colorLightTopBarCloseButton,
    backgroundShimmerItem: Color = colorLightBackgroundShimmerItem,
    backgroundExpandedPaymentDetails: Color = colorLightBackgroundExpandedPaymentDetails,
    expandedPaymentDetailsCloseButton: Color = colorLightExpandedPaymentDetailsCloseButton,
    backgroundPaymentMethods: Color = colorLightBackgroundPaymentMethods,
    backgroundPaymentMethodItem: Color = colorLightBackgroundPaymentMethodItem,
    borderPaymentMethodItem: Color = colorLightBorderPaymentMethodItem,
    footerContent: Color = colorLightFooterContent
): SDKColors = SDKColors(
    brand = brand ?: colorLightBrand,
    backgroundTopBar = backgroundTopBar,
    topBarCloseButton = topBarCloseButton,
    backgroundShimmerItem = backgroundShimmerItem,
    backgroundExpandedPaymentDetails = backgroundExpandedPaymentDetails,
    expandedPaymentDetailsCloseButton = expandedPaymentDetailsCloseButton,
    backgroundPaymentMethods = backgroundPaymentMethods,
    backgroundPaymentMethodItem = backgroundPaymentMethodItem,
    borderPaymentMethodItem = borderPaymentMethodItem,
    footerContent = footerContent,
    isLight = true
)

internal fun darkColors(
    brand: Color? = null,
    backgroundTopBar: Color = colorDarkBackgroundTopBar,
    topBarCloseButton: Color = brand ?: colorDarkTopBarCloseButton,
    backgroundShimmerItem: Color = colorDarkBackgroundShimmerItem,
    backgroundExpandedPaymentDetails: Color = colorDarkBackgroundExpandedPaymentDetails,
    expandedPaymentDetailsCloseButton: Color = colorDarkExpandedPaymentDetailsCloseButton,
    backgroundPaymentMethods: Color = colorDarkBackgroundPaymentMethods,
    backgroundPaymentMethodItem: Color = colorDarkBackgroundPaymentMethodItem,
    borderPaymentMethodItem: Color = colorDarkBorderPaymentMethodItem,
    footerContent: Color = colorDarkFooterContent
): SDKColors = SDKColors(
    brand = brand ?: colorDarkBrand,
    backgroundTopBar = backgroundTopBar,
    topBarCloseButton = topBarCloseButton,
    backgroundShimmerItem = backgroundShimmerItem,
    backgroundExpandedPaymentDetails = backgroundExpandedPaymentDetails,
    expandedPaymentDetailsCloseButton = expandedPaymentDetailsCloseButton,
    backgroundPaymentMethods = backgroundPaymentMethods,
    backgroundPaymentMethodItem = backgroundPaymentMethodItem,
    borderPaymentMethodItem = borderPaymentMethodItem,
    footerContent = footerContent,
    isLight = false
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }