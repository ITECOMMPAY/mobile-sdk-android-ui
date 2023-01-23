package com.ecommpay.ui.msdk.sample.domain.ui.main

import android.net.Uri
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.domain.ui.base.ViewIntents

sealed interface MainViewIntents: ViewIntents {
    //Changing PaymentData fields
    data class ChangeField(val paymentData: PaymentData): MainViewIntents
    //Checkboxes
    object ChangeGooglePayCheckBox: MainViewIntents
    object ChangeApiHostCheckBox: MainViewIntents
    //Customization brand color and logo
    object ChangeCustomizationCheckbox: MainViewIntents
    data class SelectResourceImage(val id: Int, val paymentData: PaymentData): MainViewIntents
    data class SelectLocalImage(val uri: Uri, val paymentData: PaymentData): MainViewIntents
    data class SelectForcePaymentMethod(val id: Int, val paymentData: PaymentData): MainViewIntents
    //Custom mock mode
    object ChangeMockModeCheckbox: MainViewIntents
    data class SelectMockMode(val id: Int, val paymentData: PaymentData): MainViewIntents
    //Sale button
    object Sale: MainViewIntents
    //Auth button
    object Auth: MainViewIntents
    //Tokenize button
    object Tokenize: MainViewIntents
    object ThreeDSecure: MainViewIntents
    object Recurrent: MainViewIntents
    object Recipient: MainViewIntents
    object AdditionalFields: MainViewIntents
}