package com.paymentpage.ui.msdk.sample.ui.main.views.customization

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.main.MainViewState
import com.paymentpage.ui.msdk.sample.domain.entities.PaymentData
import com.paymentpage.ui.msdk.sample.ui.main.views.customization.customBrandColor.BrandColorPicker
import com.paymentpage.ui.msdk.sample.ui.main.views.customization.customLogo.SelectImagesList

@Composable
internal fun CustomizationFields(
    viewState: MainViewState,
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit
) {
    Spacer(modifier = Modifier.size(10.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, Color.LightGray)
            .padding(horizontal = 10.dp),
        content = {
            Spacer(modifier = Modifier.size(10.dp))
            BrandColorPicker(paymentData = paymentData) { intentListener(it) }
            Spacer(modifier = Modifier.size(10.dp))
            SelectImagesList(
                selectedResourceImageId = viewState.selectedResourceImageId,
                paymentData = paymentData
            ) { intentListener(it) }
            Spacer(modifier = Modifier.size(10.dp))
        }
    )
}