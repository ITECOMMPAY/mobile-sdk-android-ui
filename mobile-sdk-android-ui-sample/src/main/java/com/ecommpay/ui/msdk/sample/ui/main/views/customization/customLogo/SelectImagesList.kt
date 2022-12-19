package com.ecommpay.ui.msdk.sample.ui.main.views.customization.customLogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.entities.PaymentData
import com.ecommpay.ui.msdk.sample.utils.extensions.bitmapFromUri
import com.ecommpay.ui.msdk.sample.utils.extensions.resIdByName
import com.ecommpay.ui.msdk.sample.utils.extensions.uriFromResourceId
import com.ecommpay.ui.msdk.sample.R


@Composable
internal fun SelectImagesList(
    selectedResourceImageId: Int,
    paymentData: PaymentData,
    intentListener: (MainViewIntents) -> Unit,
) {
    val context = LocalContext.current
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
                    selected = index == selectedResourceImageId,
                    onClick = {
                        intentListener(
                            MainViewIntents.SelectResourceImage(
                            id = index,
                            paymentData = paymentData.copy(bitmap = bitmap)))
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
    val bitmapFinalImage = paymentData.bitmap?.asImageBitmap()
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
        intentListener(
            MainViewIntents.SelectLocalImage(it,
            paymentData = paymentData.copy(bitmap = bitmapFromUri(it, context))))
    }
    Spacer(modifier = Modifier.size(10.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            intentListener(
                MainViewIntents.SelectResourceImage(
                id = -1,
                paymentData = paymentData.copy(bitmap = null)
            ))
        }
    ) {
        Text(text = "Reset logo", color = Color.White, fontSize = 18.sp)
    }
}