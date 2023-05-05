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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.paymentMethodLogoId

@Composable
internal fun ExpandablePaymentMethodItem(
    method: UIPaymentMethod,
    isOnlyOneMethodOnScreen: Boolean = false,
    fallbackIcon: Painter? = null,
    iconColor: ColorFilter? = null,
    isLocalResourceIcon: Boolean = method.logoUrl.isNullOrEmpty() &&
            method.paymentMethod.iconUrl.isNullOrEmpty(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val currentMethod = paymentMethodsViewModel.state.collectAsState().value.currentMethod
    val rotationState by animateFloatAsState(
        if (currentMethod?.index == method.index) 180f else 0f
    )
    val topContentIsEmpty = method.title.isEmpty() && fallbackIcon == null
    val isExpanded = currentMethod?.index == method.index
    val isDarkTheme = SDKTheme.colors.isDarkTheme

    val sectionRoleContentDescription = stringResource(id = R.string.section_role_content_description)
    val expandedStateContentDescription = stringResource(id = R.string.expanded_state_content_description)
    val collapsedStateContentDescription = stringResource(id = R.string.collapsed_state_content_description)

    val context = LocalContext.current
    val drawableId = remember(method) {
        context.paymentMethodLogoId(
            paymentMethodType = method.paymentMethod.paymentMethodType,
            paymentMethodName = if (method is UIPaymentMethod.UISavedCardPayPaymentMethod)
                method.savedAccount.cardType ?: ""
            else
                method.paymentMethod.code,
            isDarkTheme = isDarkTheme
        )
    }

    val imageTestTag = "${
        when(method) {
            is UIPaymentMethod.UICardPayPaymentMethod -> TestTagsConstants.PREFIX_NEW_CARD
            is UIPaymentMethod.UISavedCardPayPaymentMethod -> TestTagsConstants.PREFIX_SAVE_CARD
            is UIPaymentMethod.UIGooglePayPaymentMethod -> TestTagsConstants.PREFIX_GOOGLE_PAY
            is UIPaymentMethod.UIApsPaymentMethod -> TestTagsConstants.PREFIX_APS
        }
    }${
        if (isDarkTheme) TestTagsConstants.DARK_THEME else TestTagsConstants.LIGHT_THEME
    }${
        if (isLocalResourceIcon) TestTagsConstants.POSTFIX_LOCAL else TestTagsConstants.POSTFIX_REMOTE
    }${
        if (drawableId == 0) TestTagsConstants.POSTFIX_DEFAULT else ""
    }${
        TestTagsConstants.POSTFIX_ICON
    }"

    Box(
        modifier = Modifier
            .background(
                color = if (!SDKTheme.colors.isDarkTheme)
                    SDKTheme.colors.background
                else
                    SDKTheme.colors.container,
                shape = SDKTheme.shapes.radius8
            )
            .border(
                width = 1.dp,
                color = if (!SDKTheme.colors.isDarkTheme)
                    SDKTheme.colors.highlight
                else
                    SDKTheme.colors.container,
                shape = SDKTheme.shapes.radius8
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
        ) {
            if (!topContentIsEmpty)
                Row(
                    modifier =
                    if (!isOnlyOneMethodOnScreen)
                        Modifier
                            .clickable(
                                indication = null, //turned off animation
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    if (currentMethod?.index != method.index)
                                        paymentMethodsViewModel.setCurrentMethod(method)
                                    else
                                        paymentMethodsViewModel.setCurrentMethod(null)
                                }
                            )
                            .height(50.dp)
                            .padding(15.dp)
                            .semantics {
                                contentDescription = sectionRoleContentDescription
                                stateDescription = if (isExpanded)
                                    expandedStateContentDescription
                                else
                                    collapsedStateContentDescription
                            }
                            .testTag(TestTagsConstants.PAYMENT_METHOD_ITEM_SECTION)
                    else
                        Modifier
                            .height(50.dp)
                            .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isLocalResourceIcon) {
                        AsyncImage(
                            modifier = Modifier
                                .size(height = 20.dp, width = 50.dp)
                                .testTag(imageTestTag),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(
                                    if (!method.logoUrl.isNullOrEmpty())
                                        method.logoUrl
                                    else
                                        method.paymentMethod.iconUrl
                                )
                                .crossfade(true)
                                .diskCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            fallback = fallbackIcon,
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                            placeholder = fallbackIcon,
                            alignment = Alignment.CenterStart,
                        )
                    } else {
                        if (fallbackIcon != null)
                            Image(
                                modifier = Modifier
                                    .testTag(imageTestTag),
                                painter = if (drawableId > 0)
                                    painterResource(id = drawableId)
                                else
                                    fallbackIcon,
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                colorFilter = if (drawableId == 0)
                                    iconColor
                                else
                                    null,
                            )
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.PAYMENT_METHOD_ITEM_TITLE_TEXT),
                            text = method.title,
                            textAlign = TextAlign.Center,
                            style = SDKTheme.typography.s14Normal
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        if (!isOnlyOneMethodOnScreen) {
                            Image(
                                modifier = Modifier
                                    .rotate(rotationState),
                                imageVector = Icons.Default.KeyboardArrowDown,
                                colorFilter = ColorFilter.tint(SDKTheme.colors.neutral),
                                contentDescription = null,
                            )
                        }
                    }
                }
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = if (!isOnlyOneMethodOnScreen)
                                10.dp
                            else if (topContentIsEmpty)
                                5.dp
                            else
                                0.dp,
                            bottom = 20.dp
                        ),
                    content = content
                )
            }
        }
    }
}