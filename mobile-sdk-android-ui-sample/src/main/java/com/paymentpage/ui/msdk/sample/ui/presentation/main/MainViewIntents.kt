package com.paymentpage.ui.msdk.sample.ui.presentation.main

import android.net.Uri
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData

sealed interface MainViewIntents: ViewIntents {
    data class ChangeField(val paymentData: PaymentData): MainViewIntents
    object ChangeGooglePayCheckBox: MainViewIntents
    object ChangeApiHostCheckBox: MainViewIntents
    object ExpandResourceImagesList: MainViewIntents
    data class SelectResourceImage(val id: Int, val paymentData: PaymentData): MainViewIntents
    data class SelectLocalImage(val uri: Uri, val paymentData: PaymentData): MainViewIntents
    object Init: MainViewIntents
    object Sale: MainViewIntents
}