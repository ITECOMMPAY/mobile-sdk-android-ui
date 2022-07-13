package com.ecommpay.msdk.ui.views.common

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
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme

@Composable
internal fun CardView(
    brandLogoUrl: String? = null,
    amount: String,
    currency: String?,
    vatIncludedTitle: String? = null,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SDKTheme.dimensions.padding20),
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                brandLogoUrl?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it)
                            .crossfade(true)
                            .build(),
                        fallback = painterResource(SDKTheme.images.sdkLogoResId),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(height = 50.dp, width = 100.dp)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.baselinePadding(
                            firstBaselineToTop = 0.dp,
                            lastBaselineToBottom = 0.dp
                        ),
                        text = amount,
                        style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                    )
                    if (currency != null) {
                        Text(
                            text = " "
                        )
                        Text(
                            modifier = Modifier.baselinePadding(
                                firstBaselineToTop = 0.dp,
                                lastBaselineToBottom = 0.dp
                            ),
                            text = currency,
                            style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(SDKTheme.dimensions.padding10))
                Row {
                    Text(
                        text = stringResource(R.string.total_price_label),
                        style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                    )
                    Text(
                        text = " "
                    )
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

fun Modifier.baselinePadding(
    firstBaselineToTop: Dp,
    lastBaselineToBottom: Dp
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    check(placeable[LastBaseline] != AlignmentLine.Unspecified)
    val lastBaseline = placeable[LastBaseline]

    val lastBaselineToBottomHeight = placeable.height - lastBaseline

    val lastBaselineToBottomDelta = lastBaselineToBottom.roundToPx() - lastBaselineToBottomHeight

    val totalHeight = placeable.height +
            (firstBaselineToTop.roundToPx() - firstBaseline)

    val placeableY = totalHeight - placeable.height
    layout(placeable.width, totalHeight + lastBaselineToBottomDelta) {
        placeable.placeRelative(0, placeableY)
    }
}


@Composable
@Preview
fun CardViewPreview() {
    CardView(
        brandLogoUrl = "url",
        amount = "238.00",
        currency = "EUR",
        vatIncludedTitle = ""
    )
}
