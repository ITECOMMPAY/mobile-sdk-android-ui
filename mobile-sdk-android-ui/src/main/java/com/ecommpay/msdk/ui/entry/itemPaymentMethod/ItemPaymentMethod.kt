package com.ecommpay.msdk.ui.entry.itemPaymentMethod

import com.ecommpay.msdk.ui.R
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ecommpay.msdk.ui.theme.SDKTheme


@Composable
fun ItemPaymentMethod(
    name: String,
    iconUrl: String? = "",
    intentListener: (intent: ItemPaymentMethodIntents) -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { intentListener(ItemPaymentMethodIntents.Click(name = name)) }
            .fillMaxWidth()
            .height(SDKTheme.dimensions.paymentMethodItemHeight)
            .background(
                color = SDKTheme.colors.backgroundPaymentMethodItem,
                shape = SDKTheme.shapes.radius6
            )
            .border(width = 1.dp, color = SDKTheme.colors.borderPaymentMethodItem),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(SDKTheme.dimensions.paddingDp15)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(iconUrl)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(
                    id = if (SDKTheme.colors.isLight)
                        R.drawable.sdk_card_logo_light
                    else R.drawable.sdk_card_logo_dark
                ),
                contentDescription = "",
                contentScale = ContentScale.Inside,
                placeholder = painterResource(
                    id = if (SDKTheme.colors.isLight)
                        R.drawable.sdk_card_logo_light
                    else R.drawable.sdk_card_logo_dark
                ),
                modifier = Modifier.size(height = 20.dp, width = 50.dp)
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = SDKTheme.typography.s14Normal
                )
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
                Image(
                    modifier = Modifier.clickable(
                        indication = null, //отключаем анимацию при клике
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            intentListener(ItemPaymentMethodIntents.Click(name = name))
                        }
                    ),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    colorFilter = ColorFilter.tint(SDKTheme.colors.topBarCloseButton),
                    contentDescription = "",
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemPaymentMethodPreview() {
    ItemPaymentMethod(
        name = "Bank card",
        iconUrl = null,
        intentListener = {})
}


