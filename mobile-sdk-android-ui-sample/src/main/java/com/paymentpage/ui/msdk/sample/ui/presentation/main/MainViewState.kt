package com.paymentpage.ui.msdk.sample.ui.presentation.main

import android.net.Uri
import com.paymentpage.ui.msdk.sample.ui.presentation.base.ViewState
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData

data class MainViewState(
    var paymentData: PaymentData,
    var isVisibleApiHostFields: Boolean,
    var isVisibleGooglePayFields: Boolean,
    var isExpandedSelectImagesList: Boolean,
    var selectedResourceImageId: Int,
    var localImageUri: Uri?
) : ViewState