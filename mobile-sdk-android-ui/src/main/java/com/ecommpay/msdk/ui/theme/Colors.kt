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
}

val BrandLight = Color(0xFF00579E)

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

internal fun lightColors(brand: Color = colorLightBrand): SDKColors = SDKColors(
    brand = brand,
    backgroundTopBar = colorLightBackgroundTopBar,
    topBarCloseButton = colorLightTopBarCloseButton,
    backgroundShimmerItem = colorLightBackgroundShimmerItem,
    backgroundExpandedPaymentDetails = colorLightBackgroundExpandedPaymentDetails,
    expandedPaymentDetailsCloseButton = colorLightExpandedPaymentDetailsCloseButton,
    backgroundPaymentMethods = colorLightBackgroundPaymentMethods,
    backgroundPaymentMethodItem = colorLightBackgroundPaymentMethodItem,
    borderPaymentMethodItem = colorLightBorderPaymentMethodItem,
    footerContent = colorLightFooterContent,
)


internal val LocalColors = staticCompositionLocalOf { lightColors() }