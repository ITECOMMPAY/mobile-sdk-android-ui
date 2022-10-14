@file:Suppress("PrivatePropertyName", "unused", "MemberVisibilityCanBePrivate")

package com.ecommpay.msdk.ui

import android.graphics.Bitmap
import com.paymentpage.msdk.core.domain.entities.RecipientInfo
import com.paymentpage.msdk.ui.base.PaymentOptionsDsl


fun paymentOptions(block: EcmpPaymentOptions.() -> Unit): EcmpPaymentOptions =
    EcmpPaymentOptions().apply(block)

/**
 * Payment configuration
 */
@PaymentOptionsDsl
class EcmpPaymentOptions {
    lateinit var paymentInfo: EcmpPaymentInfo

    var recurrentData: EcmpRecurrentData? = null
    var recipientInfo: RecipientInfo? = null
    var actionType: EcmpActionType = EcmpActionType.Sale
    //var bankId: Int? = null

    var logoImage: Bitmap? = null
    var brandColor: String? = null

    var merchantId: String = ""
    var merchantName: String = ""
    var isTestEnvironment: Boolean = true

    private val _additionalFields = mutableListOf<EcmpAdditionalField>()
    val additionalFields: List<EcmpAdditionalField>
        get() = additionalFields.toList()

    fun additionalFields(block: EcmpAdditionalFields.() -> Unit) {
        _additionalFields.addAll(EcmpAdditionalFields().apply(block))
    }
}