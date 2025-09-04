package com.ecommpay.ui.msdk.sample.data

import android.graphics.Bitmap
import com.ecommpay.msdk.ui.EcmpActionType
import com.ecommpay.msdk.ui.EcmpAdditionalField
import com.ecommpay.msdk.ui.EcmpAdditionalFieldType
import com.ecommpay.msdk.ui.Ecommpay
import com.ecommpay.msdk.ui.EcmpScreenDisplayMode
import com.ecommpay.ui.msdk.sample.data.storage.entities.CommonJson
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.domain.entities.RecipientData
import com.ecommpay.ui.msdk.sample.domain.entities.RecurrentData

object ProcessRepository {
    var additionalFields: List<EcmpAdditionalField> =
        EcmpAdditionalFieldType.values().map { EcmpAdditionalField(type = it) }
    var mockModeType: Ecommpay.EcmpMockModeType = Ecommpay.EcmpMockModeType.DISABLED
    var actionType: EcmpActionType = EcmpActionType.Sale
    var screenDisplayModes: List<EcmpScreenDisplayMode> = listOf(EcmpScreenDisplayMode.DEFAULT)
    var paymentData: PaymentData = PaymentData()
    var hideScanningCards: Boolean = false
    var storedCardType: Int? = null

    //Recurrent
    var recurrentData: RecurrentData? = null
    var isEnabledRecurrent: Boolean = false

    //Recipient
    var recipientData: RecipientData? = null
    var isEnabledRecipient: Boolean = false

    //3DS
    var jsonThreeDSecureInfo: String? = null
    var commonJson: CommonJson? = null
    var isEnabledThreeDSecure: Boolean = false

    //Theme
    var brandColor: String? = null
    var bitmap: Bitmap? = null
    var isDarkTheme: Boolean = false
}