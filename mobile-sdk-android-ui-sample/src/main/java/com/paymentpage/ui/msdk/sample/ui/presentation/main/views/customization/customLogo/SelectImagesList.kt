package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.customization.customLogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.models.PaymentData
import com.paymentpage.ui.msdk.sample.utils.extensions.bitmapFromUri
import com.paymentpage.ui.msdk.sample.utils.extensions.resIdByName
import com.paymentpage.ui.msdk.sample.utils.extensions.uriFromResourceId
import com.paymentpage.ui.sample.R


@Composable
internal fun SelectImagesList(
    viewModel: MainViewModel = viewModel(),
) {
    val context = LocalContext.current
    val viewState by viewModel.viewState.collectAsState()
    val drawablesFields = R.drawable::class.java.fields.filter { it.name.contains("_logo") }
    drawablesFields.forEachIndexed { index, field ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically
        ) {
            val resourceId = context.resIdByName(field.name, "drawable")
            val bitmap = bitmapFromUri(uriFromResourceId(resourceId, context), context)
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(
                    selected = index == viewState?.selectedResourceImageId,
                    onClick = {
                        viewModel.pushIntent(MainViewIntents.SelectResourceImage(
                            id = index,
                            paymentData = viewState?.paymentData?.copy(bitmap = bitmap)
                                ?: PaymentData.defaultPaymentData))
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = field.name.removeSuffix("_logo").replace("_", " ").uppercase())
            }
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)
                    .background(Color.LightGray),
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
    Spacer(modifier = Modifier.size(10.dp))
    Text(text = "Current logo:", color = Color.Black, fontSize = 18.sp)
    Spacer(modifier = Modifier.size(10.dp))
    val bitmapFinalImage = viewState?.paymentData?.bitmap?.asImageBitmap()
    if (bitmapFinalImage != null) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray),
            bitmap = bitmapFinalImage,
            contentDescription = null)
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
                .border(width = 1.dp, color = Color.LightGray),
        )
    }
    Spacer(modifier = Modifier.size(10.dp))
    SelectLocalImage {
        viewModel.pushIntent(MainViewIntents.SelectLocalImage(it,
            paymentData = viewState?.paymentData?.copy(bitmap = bitmapFromUri(it, context))
                ?: PaymentData.defaultPaymentData))
    }
    Spacer(modifier = Modifier.size(10.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            viewModel.pushIntent(MainViewIntents.SelectResourceImage(
                id = -1,
                paymentData = viewState?.paymentData?.copy(bitmap = null)
                    ?: PaymentData.defaultPaymentData
            ))
        }
    ) {
        Text(text = "Reset logo", color = Color.White, fontSize = 18.sp)
    }
}