package com.paymentpage.msdk.ui.views.expandable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun ExpandableItem(
    index: Int,
    iconUrl: String? = null,
    fallbackIcon: Painter,
    name: String? = null,
    onExpand: (index: Int) -> Unit,
    onCollapse: (index: Int) -> Unit,
    isExpanded: Boolean = false,
    headerBackgroundColor: Color = SDKTheme.colors.backgroundColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    val rotationState by animateFloatAsState(if (isExpanded) 180f else 0f)
    Box(
        modifier = Modifier
            .background(
                color = if (isExpanded) SDKTheme.colors.backgroundColor else headerBackgroundColor,
                shape = SDKTheme.shapes.radius6
            )
            .border(
                width = 1.dp,
                color = SDKTheme.colors.borderColor,
                shape = SDKTheme.shapes.radius6
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 400,
                    easing = LinearOutSlowInEasing
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SDKTheme.dimensions.padding15)
        ) {
            Row(modifier = Modifier
                .clickable(
                    indication = null, //отключаем анимацию при клике
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (!isExpanded)
                            onExpand(index)
                        else
                            onCollapse(index)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    fallback = fallbackIcon,
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    placeholder = fallbackIcon,
                    modifier = Modifier.size(height = 20.dp, width = 50.dp),
                    alignment = Alignment.CenterStart
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!name.isNullOrEmpty())
                        Text(
                            text = name,
                            textAlign = TextAlign.Center,
                            style = SDKTheme.typography.s14Normal
                        )
                    Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
                    Image(
                        modifier = Modifier
                            .rotate(rotationState),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        colorFilter = ColorFilter.tint(SDKTheme.colors.iconColor),
                        contentDescription = "",
                    )
                }
            }
            if (isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    content = content
                )
            }
        }
    }
}

@Composable
@Preview
fun ExpandablePaymentMethodItemPreview() {
    ExpandableItem(
        index = 0,
        name = "Bank card",
        fallbackIcon = painterResource(id = SDKTheme.images.cardLogoResId),
        onExpand = {},
        onCollapse = {}
    ) {
        Text(text = "sdfsdfsdf") // testing content (delete later)
    }
}