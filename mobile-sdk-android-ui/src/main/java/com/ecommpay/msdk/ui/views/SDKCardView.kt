package com.ecommpay.msdk.ui.views

import androidx.appcompat.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
fun SDKCardView(
    brandLogoUrl: String,
    price: String,
    currency: String,
    vatIncluded: Boolean = false,
    totalPriceTitle: String,
    vatIncludedTitle: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                color = SDKTheme.colors.brand,
                shape = SDKTheme.shapes.radius12
            )
            .padding(SDKTheme.dimensions.paddingDp20)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(brandLogoUrl)
                        .crossfade(true)
                        .build(),
                    fallback = painterResource(R.drawable.abc_star_black_48dp),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(height = 50.dp, width = 100.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = price,
                        style = SDKTheme.typography.s28Bold.copy(color = Color.White),
                    )
                    Text(
                        text = " "
                    )
                    Text(
                        text = currency,
                        style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                    )
                }
                if (vatIncluded) {
                    Row {
                        Text(
                            text = totalPriceTitle,
                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                        )
                        Text(
                            text = " "
                        )
                        Text(
                            text = vatIncludedTitle,
                            style = SDKTheme.typography.s14Light.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SDKCardViewPreview() {
    SDKCardView(
        brandLogoUrl = "url",
        price = "238.00",
        currency = "EUR",
        totalPriceTitle = "Total price",
        vatIncluded = true,
        vatIncludedTitle = "(VAT included)"
    )
}
