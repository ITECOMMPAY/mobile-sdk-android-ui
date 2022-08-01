package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.forcePaymentMethod

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType

@Composable
internal fun SelectForcePaymentMethod(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    PaymentMethodType.values().forEachIndexed { index, paymentMethodType ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = index == viewState?.selectedForcePaymentMethodId,
                onClick = {
                    viewModel.pushIntent(MainViewIntents.SelectForcePaymentMethod(
                        id = index,
                        paymentData = viewState?.paymentData?.copy(forcePaymentMethod = paymentMethodType) ?: PaymentData.defaultPaymentData
                    ))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = paymentMethodType.name.replace("_", " "))
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
}