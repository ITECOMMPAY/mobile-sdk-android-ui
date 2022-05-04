package com.ecommpay.msdk.ui.entry.itemSaveCard

import androidx.appcompat.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ItemSaveCard(
    viewData: ItemSaveCardViewData,
    isEditable: Boolean,
    iconCardUrl: String?,
    intentListener: (intent: ItemSaveCardIntents) -> Unit,
) {
    val padding = 12.dp
    val width = 140.dp
    val height = 60.dp
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .size(
                width = width + padding,
                height = height + padding
            )
    ) {
        Card(
            elevation = 8.dp,
            modifier = Modifier
                .padding(
                    top = padding,
                    end = padding
                )
        ) {
            Row(
                modifier = Modifier
                    .clickable { intentListener(viewData.clickIntent) }
                    .size(
                        width = width,
                        height = height
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconCardUrl)
                        .crossfade(true)
                        .build(),
                    fallback = painterResource(R.drawable.abc_star_black_48dp),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(width = 70.dp, height = 50.dp)
                        .padding(10.dp)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = viewData.cardNumber
                )
            }
        }


        if (isEditable && viewData.isShowDeleteIcon)
            Image(
                painter = painterResource(
                    id = R.drawable.abc_star_black_48dp),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { intentListener(viewData.deleteIntent) })
    }
}