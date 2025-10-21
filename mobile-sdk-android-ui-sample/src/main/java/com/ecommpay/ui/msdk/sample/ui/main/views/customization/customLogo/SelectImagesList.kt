package com.ecommpay.ui.msdk.sample.ui.main.views.customization.customLogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.ecommpay.ui.msdk.sample.R
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewIntents
import com.ecommpay.ui.msdk.sample.domain.ui.main.MainViewState
import com.ecommpay.ui.msdk.sample.utils.extensions.bitmapFromUri
import com.ecommpay.ui.msdk.sample.utils.extensions.resIdByName
import com.ecommpay.ui.msdk.sample.utils.extensions.uriFromResourceId

private const val SKIPPED_DRAWABLE_NAME = "default"

@Composable
internal fun SelectImagesList(
    selectedResourceImageId: Int,
    viewState: MainViewState,
    intentListener: (MainViewIntents) -> Unit,
) {
    val context = LocalContext.current
    val drawablesFields = R.drawable::class.java.fields.filter {
        it.name.contains("_logo") && it.name.contains(SKIPPED_DRAWABLE_NAME).not()
    }

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
                                bitmap = bitmap
                            )
                        )
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
    val bitmapFinalImage = viewState.bitmap?.asImageBitmap()
    if (bitmapFinalImage != null) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.LightGray),
            bitmap = bitmapFinalImage,
            contentDescription = null
        )
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
    SelectLocalImage { uri ->
        intentListener(
            MainViewIntents.SelectLocalImage(
                uri = uri,
                bitmap = bitmapFromUri(uri, context)
            )
        )
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
                    bitmap = null
                )
            )
        }
    ) {
        Text(text = "Reset logo", color = Color.White, fontSize = 18.sp)
    }
}