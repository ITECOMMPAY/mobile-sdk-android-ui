package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customBrandColor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import com.paymentpage.ui.msdk.sample.utils.HexToJetpackColor
import com.paymentpage.ui.msdk.sample.utils.extensions.toHexCode
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun BrandColorPicker(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    val paymentData = viewState?.paymentData ?: PaymentData.defaultPaymentData
    val dialogState = rememberMaterialDialogState()

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = paymentData.brandColor?.uppercase() ?: "#",
            onValueChange = {
                viewModel.pushIntent(MainViewIntents.ChangeField(
                    paymentData.copy(brandColor = (if (it.length in 0..7) it else it.substring(0,
                        7)).uppercase())
                ))
            },
            modifier = Modifier.weight(1f),
            label = { Text(text = "Brand color") }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .clip(CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(HexToJetpackColor.getColor(paymentData.brandColor)
                        ?: Color.White)
                    .clickable {
                        dialogState.show()
                    }
            )
        }
    }
    ColorPickerDialog(dialogState = dialogState) {
        viewModel.pushIntent(MainViewIntents.ChangeField(
            paymentData = paymentData.copy(brandColor = it.toHexCode())
        ))
    }
}