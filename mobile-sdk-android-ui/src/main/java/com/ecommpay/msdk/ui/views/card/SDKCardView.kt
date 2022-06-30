package com.ecommpay.msdk.ui.views.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
internal fun SDKCardView(
    brandLogoUrl: String? = null,
    price: String,
    currency: String,
    vatIncludedTitle: String? = null,
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
                brandLogoUrl?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it)
                            .crossfade(true)
                            .build(),
                        fallback = painterResource(R.drawable.ic_sdk_logo),
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
                        text = price,
                        style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                    )
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
                if (!vatIncludedTitle.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(SDKTheme.dimensions.paddingDp10))
                    Row {
                        Text(
                            text = stringResource(R.string.total_price_label),
                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                        )
                        Text(
                            text = " "
                        )
                        Text(
                            text = "(${vatIncludedTitle})",
                            style = SDKTheme.typography.s14Light.copy(color = Color.White)
                        )
                    }
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
fun SDKCardViewPreview() {
    SDKCardView(
        brandLogoUrl = "url",
        price = "238.00",
        currency = "EUR",
        vatIncludedTitle = stringResource(id = R.string.vat_included_label)
    )
}
