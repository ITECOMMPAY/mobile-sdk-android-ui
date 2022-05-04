package com.ecommpay.msdk.ui.entry.itemPaymentMethod

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ItemPaymentMethod(
    name: String,
    iconUrl: String,
    intentListener: (intent: ItemPaymentMethodIntents) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { intentListener(ItemPaymentMethodIntents.Click(name = name)) }
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            fallback = painterResource(androidx.appcompat.R.drawable.abc_star_black_48dp),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = name,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
            ),
        )
    }
}


