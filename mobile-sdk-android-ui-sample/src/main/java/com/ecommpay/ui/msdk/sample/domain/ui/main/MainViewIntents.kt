package com.ecommpay.ui.msdk.sample.domain.ui.main

import android.graphics.Bitmap
import android.net.Uri
import com.ecommpay.msdk.ui.Ecommpay
import com.ecommpay.msdk.ui.EcmpScreenDisplayMode
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface MainViewIntents : ViewIntents {
    //Changing PaymentData fields
    data class ChangeField(val paymentData: PaymentData) : MainViewIntents

    //Checkboxes
    object ChangeGooglePayCheckBox : MainViewIntents
    object ChangeApiHostCheckBox : MainViewIntents
    object ChangeHideScanningCardsCheckbox : MainViewIntents

    //Customization brand color and logo
    object ChangeThemeCheckbox : MainViewIntents
    object ChangeCustomizationCheckbox : MainViewIntents
    data class SelectResourceImage(val id: Int, val bitmap: Bitmap?) : MainViewIntents
    data class SelectLocalImage(val uri: Uri, val bitmap: Bitmap?) : MainViewIntents
    data class ChangeBrandColor(val primaryBrandColor: String? = null, val secondaryBrandColor: String? = null) : MainViewIntents
    data class SelectForcePaymentMethod(val id: Int, val paymentData: PaymentData) : MainViewIntents

    //Stored card type
    data class ChangeStoredCardType(val storedCardType: String) : MainViewIntents

    //Custom mock mode
    object ChangeMockModeCheckbox : MainViewIntents
    data class SelectMockMode(val mockModeType: Ecommpay.EcmpMockModeType) : MainViewIntents

    //Custom screen display mode
    object ChangeScreenDisplayModeCheckbox : MainViewIntents
    data class SelectScreenDisplayMode(val screenDisplayMode: EcmpScreenDisplayMode) :
        MainViewIntents

    //Sale button
    object Sale : MainViewIntents

    //Auth button
    object Auth : MainViewIntents

    //Verify button
    object Verify : MainViewIntents

    //Tokenize button
    object Tokenize : MainViewIntents
    object ThreeDSecure : MainViewIntents
    object Recurrent : MainViewIntents
    object Recipient : MainViewIntents
    object AdditionalFields : MainViewIntents
}