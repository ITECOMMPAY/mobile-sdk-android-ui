package com.paymentpage.ui.msdk.sample.domain.ui.main

import android.net.Uri
import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI
import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewIntents
import com.paymentpage.ui.msdk.sample.domain.entities.PaymentData

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
    //Tokenize button
    object Tokenize: MainViewIntents
    object ThreeDSecure: MainViewIntents
    object Recurrent: MainViewIntents
    object AdditionalFields: MainViewIntents
}