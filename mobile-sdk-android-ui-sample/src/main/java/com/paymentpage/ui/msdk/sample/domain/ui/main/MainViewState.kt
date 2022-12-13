package com.paymentpage.ui.msdk.sample.domain.ui.main

import android.net.Uri
import com.paymentpage.ui.msdk.sample.domain.ui.base.ViewState
import com.paymentpage.ui.msdk.sample.domain.entities.PaymentData
import com.paymentpage.ui.msdk.sample.domain.ui.base.MessageUI

data class MainViewState(
    val paymentData: PaymentData = PaymentData(),
    val isVisibleForcePaymentMethodFields: Boolean = false,
    val isVisibleApiHostFields: Boolean = false,
    val isVisibleGooglePayFields: Boolean = false,
    val isVisibleCustomizationFields: Boolean = false,
    val isVisibleMockModeType: Boolean = false,
    val selectedResourceImageId: Int = -1,
    val selectedForcePaymentMethodId: Int = -1,
    val selectedMockModeTypeId: Int = 0,
    val localImageUri: Uri? = null,
) : ViewState