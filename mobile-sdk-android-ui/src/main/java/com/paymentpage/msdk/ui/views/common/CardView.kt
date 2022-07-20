package com.paymentpage.msdk.ui.views.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun CardView(
    logoImage: Bitmap? = null,
    amount: String,
    currency: String?,
    vatIncludedTitle: String? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (logoImage != null) 150.dp else 95.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_lines_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(
                color = SDKTheme.colors.brand,
                blendMode = BlendMode.Multiply
            ),
            modifier = Modifier.clip(SDKTheme.shapes.radius12)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(SDKTheme.dimensions.padding20),
        ) {
            logoImage?.let {
                Image(
                    alignment = Alignment.TopStart,
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomStart),
            ) {
                Row {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = amount,
                        style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                    )
                    if (currency != null) {
                        Spacer(
                            modifier = Modifier
                                .width(SDKTheme.dimensions.padding8)
                                .alignByBaseline()
                        )
                        Text(
                            modifier = Modifier.alignByBaseline(),
                            text = currency,
                            style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                        )
                    }
                }
                //Spacer(modifier = Modifier.height(SDKTheme.dimensions.padding10))
                Row {
                    Text(
                        text = stringResource(R.string.total_price_label),
                        style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (!vatIncludedTitle.isNullOrEmpty())
                        Text(
                            text = vatIncludedTitle,
                            style = SDKTheme.typography.s14Light.copy(color = Color.White)
                        )
                }
            }
        }
    }
}


@Composable
@Preview
fun CardViewPreview() {
    CardView(
        logoImage = Bitmap.createBitmap(100, 100, Bitmap.Config.ALPHA_8),
        amount = "238.00",
        currency = "EUR",
        vatIncludedTitle = ""
    )
}
