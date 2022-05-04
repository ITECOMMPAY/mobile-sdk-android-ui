package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import android.os.Bundle
import com.ecommpay.msdk.ui.base.BaseViewModel
import com.ecommpay.msdk.ui.base.DefaultViewStates

class EnterCVVViewModel : BaseViewModel<EnterCVVViewData>() {

    override fun entryPoint(arguments: Bundle?) {
        updateState(DefaultViewStates.Display(EnterCVVViewData(id = arguments?.getString("id") ?: "")))
    }

    override fun defaultViewData() = EnterCVVViewData.defaultViewData
}