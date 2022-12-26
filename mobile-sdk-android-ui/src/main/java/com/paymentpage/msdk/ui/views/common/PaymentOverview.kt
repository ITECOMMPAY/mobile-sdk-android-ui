package com.paymentpage.msdk.ui.views.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail.PaymentDetailsContent
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.SDKButton

@Composable
internal fun PaymentOverview(
    showPaymentDetailsButton: Boolean = true
) {
    var expandPaymentDetailsState by remember { mutableStateOf(false) }
    ExpandablePaymentOverview(
        showPaymentDetailsButton = showPaymentDetailsButton,
        isExpanded = expandPaymentDetailsState,
        onExpand = { expandPaymentDetailsState = !expandPaymentDetailsState }
    ) {
        PaymentDetailsContent(
            paymentIdLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_ID),
            paymentIdValue = LocalPaymentOptions.current.paymentInfo.paymentId,
            paymentDescriptionLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_DESCRIPTION),
            paymentDescriptionValue = LocalPaymentOptions.current.paymentInfo.paymentDescription,
            merchantAddressLabel = "",
            merchantAddressValue = null
        )
    }
}

@Composable
internal fun ExpandablePaymentOverview(
    showPaymentDetailsButton: Boolean = true,
    isExpanded: Boolean = false,
    onExpand: () -> Unit,
    expandableContent: @Composable ColumnScope.() -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val currentMethod = paymentMethodsViewModel.state.collectAsState().value.currentMethod
    val payment = mainViewModel.payment
    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()

    val gradient = arrayOf(
        0.0f to Color(0xFF0D5189),
        1f to Color(0xFF2582CE),
    )
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colorStops = gradient
                ),
                shape = SDKTheme.shapes.radius12
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(SnapSpec())
                .padding(20.dp),
        ) {
            LocalPaymentOptions.current.logoImage?.let {
                Image(
                    alignment = Alignment.TopStart,
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(35.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                    style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = LocalPaymentOptions.current.paymentInfo.paymentCurrency,
                    style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                )
            }
            Spacer(modifier = Modifier.size(6.dp))
            Row {
                Text(
                    text = getStringOverride(OverridesKeys.TITLE_TOTAL_PRICE),
                    style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                )
                Text(text = " ")
                if (currentMethod?.paymentMethod?.isVatInfo == true
                    || paymentMethods.firstOrNull {
                        if (payment != null)
                            payment.method == it.code
                        else false
                    }?.isVatInfo == true
                )
                    Text(
                        text = getStringOverride(OverridesKeys.VAT_INCLUDED),
                        style = SDKTheme.typography.s14Light.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
            }
            if (showPaymentDetailsButton) {
                Spacer(modifier = Modifier.size(20.dp))
                if (isExpanded) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(SDKTheme.colors.panelBackgroundColor.copy(alpha = 0.1f))
                    )
                }
                AnimatedVisibility(visible = isExpanded) {
                    Column(
                        content = {
                            expandableContent()
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    )
                }
                SDKButton(
                    modifier = Modifier.height(33.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    textStyle = SDKTheme.typography.s14Normal.copy(color = Color.White),
                    label = if (!isExpanded)
                        getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_SCREEN)
                    else
                        getStringOverride(OverridesKeys.BUTTON_HIDE_DETAILS),
                    isEnabled = true,
                    onClick = onExpand
                )
            }
        }
    }
}
