@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.ecommpay.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl


fun paymentOptions(block: EcmpPaymentOptions.() -> Unit): EcmpPaymentOptions =
    EcmpPaymentOptions().apply(block)

/**
 * Payment configuration
 */
@PaymentOptionsDsl
class EcmpPaymentOptions {
    //payment configuration
    lateinit var paymentInfo: EcmpPaymentInfo
    var recurrentData: EcmpRecurrentData? = null
    var recipientInfo: EcmpRecipientInfo? = null
    var actionType: EcmpActionType = EcmpActionType.Sale
    private val _additionalFields = mutableListOf<EcmpAdditionalField>()
    val additionalFields: List<EcmpAdditionalField>
        get() = _additionalFields.toList()
    fun additionalFields(block: EcmpAdditionalFields.() -> Unit) {
        _additionalFields.addAll(EcmpAdditionalFields().apply(block))
    }
    private val _screenDisplayModes = mutableListOf<EcmpScreenDisplayMode>()
    val screenDisplayModes: List<EcmpScreenDisplayMode>
        get() = _screenDisplayModes.toList()
    fun screenDisplayModes(block: EcmpScreenDisplayModes.() -> Unit) {
        _screenDisplayModes.addAll(EcmpScreenDisplayModes().apply(block))
    }
    var hideScanningCards: Boolean = false
    //google pay configuration
    var merchantId: String = ""
    var merchantName: String = ""
    var isTestEnvironment: Boolean = true
    //theme customization
    var isDarkTheme: Boolean = false
    var logoImage: Bitmap? = null
    var primaryBrandColor: String? = null
    var secondaryBrandColor: String? = null
    var footerImage: Bitmap? = null
    var footerLabel: String? = null
    var storedCardType: Int? = null
}
