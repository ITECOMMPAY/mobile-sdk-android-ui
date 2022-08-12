package com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.method.expandable

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.setCurrentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName

@Composable
internal fun ExpandablePaymentMethodItem(
    method: UIPaymentMethod,
    fallbackIcon: Painter,
    isLocalResourceIcon: Boolean = method.logoUrl.isNullOrEmpty() && method.paymentMethod.iconUrl.isNullOrEmpty(),
    prefixNameResourceIcon: String? = null,
    headerBackgroundColor: Color = SDKTheme.colors.backgroundColor,
    content: @Composable ColumnScope.() -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val currentMethod = mainViewModel.state.collectAsState().value.currentMethod
    val rotationState by animateFloatAsState(if (currentMethod?.index == method.index) 180f else 0f)
    Box(
        modifier = Modifier
            .background(
                color = if (currentMethod?.index == method.index) SDKTheme.colors.backgroundColor else headerBackgroundColor,
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
                .padding(15.dp)
        ) {
            Row(modifier = Modifier
                .clickable(
                    indication = null, //отключаем анимацию при клике
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        if (currentMethod?.index != method.index)
                            mainViewModel.setCurrentMethod(method)
                        else
                            mainViewModel.setCurrentMethod(null)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {
                if (!isLocalResourceIcon) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(method.logoUrl)
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
                } else {
                    val name = "${prefixNameResourceIcon}_${method.paymentMethod.code}_logo"
                    val context = LocalContext.current
                    val drawableId = remember(name) {
                        context.drawableResourceIdFromDrawableName(name)
                    }
                    Image(
                        painter = if (drawableId > 0) painterResource(id = drawableId) else fallbackIcon,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = method.title,
                        textAlign = TextAlign.Center,
                        style = SDKTheme.typography.s14Normal
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Image(
                        modifier = Modifier
                            .rotate(rotationState),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        colorFilter = ColorFilter.tint(SDKTheme.colors.navigationIconColor),
                        contentDescription = "",
                    )
                }
            }
            AnimatedVisibility(visible = currentMethod?.index == method.index) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    content = content
                )
            }
        }
    }
}