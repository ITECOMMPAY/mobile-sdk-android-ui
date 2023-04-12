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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail.PaymentDetailsContent
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.table.RecurrentInfoTable
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.RecurrentTypeUI
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.isShowRecurringUI
import com.paymentpage.msdk.ui.utils.extensions.core.typeUI
import com.paymentpage.msdk.ui.views.button.SDKButton


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ExpandablePaymentOverview(
    actionType: SDKActionType,
    expandable: Boolean,
) {
    val paymentOptions = LocalPaymentOptions.current

    val mainViewModel = LocalMainViewModel.current
    val mainScreenState = mainViewModel.state.collectAsState().value

    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val paymentMethodsState = paymentMethodsViewModel.state.collectAsState().value

    val currentMethod = paymentMethodsState.currentMethod
    val payment = mainViewModel.payment
    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()

    val paymentIdLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_ID)
    val paymentIdValue = paymentOptions.paymentInfo.paymentId
    val paymentDescriptionValue = paymentOptions.paymentInfo.paymentDescription
    val merchantAddressValue = null // ToDo: add merchant address

    val showExpandableContent = expandable && (paymentDescriptionValue != null ||
            merchantAddressValue != null)
    var isDividerVisible by remember { mutableStateOf(true) }

    val isShowVatInfoLabel = currentMethod?.paymentMethod?.isVatInfo == true
            || paymentMethods.firstOrNull {
        if (payment != null)
            payment.method == it.code
        else false
    }?.isVatInfo == true

    //background
    val gradient = arrayOf(
        0.0f to SDKTheme.colors.primary.copy(alpha = 0.95f),
        1f to SDKTheme.colors.primary.copy(alpha = 0.80f),
    )
    //expand state
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colorStops = gradient
                ), shape = SDKTheme.shapes.radius12
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(SnapSpec())
                .padding(20.dp),
        ) {
            paymentOptions.logoImage?.let { logo ->
                Image(
                    modifier = Modifier
                        .height(35.dp)
                        .testTag(TestTagsConstants.LOGO_IMAGE),
                    alignment = Alignment.TopStart,
                    bitmap = logo.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
                if (actionType != SDKActionType.Verify ||
                    mainScreenState.finalPaymentState == null
                )
                    Spacer(modifier = Modifier.size(20.dp))
            }

            if (
                paymentOptions.recurrentInfo != null &&
                paymentOptions.recurrentInfo.typeUI() == RecurrentTypeUI.REGULAR &&
                mainScreenState.finalPaymentState == null &&
                paymentOptions.recurrentInfo.isShowRecurringUI()
            ) {
                RecurrentInfoTable(
                    actionType = actionType,
                    paymentInfo = paymentOptions.paymentInfo,
                    recurrentInfo = paymentOptions.recurrentInfo,
                    labelTextStyle = SDKTheme.typography.s14Light.copy(color = Color.White),
                    spaceBetweenItems = 12.dp,
                    isTableEmptyCallback = { isDividerVisible = !it }
                )
                if (isDividerVisible) {
                    Spacer(modifier = Modifier.size(20.dp))
                    SDKDivider()
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }

            if (actionType != SDKActionType.Verify) {
                val coinsText =
                    LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins()
                val currencyText = paymentOptions.paymentInfo.paymentCurrency
                Box(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "$coinsText $currencyText"
                        }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.COINS_VALUE_TEXT),
                            text = coinsText,
                            style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.CURRENCY_VALUE_TEXT),
                            text = currencyText,
                            style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(6.dp))
                val totalPriceTitle = getStringOverride(OverridesKeys.TITLE_TOTAL_PRICE)
                val vatIncludedLabel = getStringOverride(OverridesKeys.VAT_INCLUDED)
                Box(
                    modifier = Modifier
                        .semantics {
                            contentDescription = if (isShowVatInfoLabel)
                                "$totalPriceTitle $vatIncludedLabel"
                            else
                                totalPriceTitle
                        }
                ) {
                    Row {
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.TOTAL_PRICE_TITLE_TEXT),
                            text = totalPriceTitle,
                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                        )
                        Text(
                            modifier = Modifier
                                .semantics {
                                    invisibleToUser()
                                },
                            text = " "
                        )
                        if (isShowVatInfoLabel)
                            Text(
                                modifier = Modifier
                                    .testTag(TestTagsConstants.VAT_INCLUDED_LABEL_TEXT),
                                text = vatIncludedLabel,
                                style = SDKTheme.typography.s14Light.copy(color = Color.White),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                    }
                }
            } else {
                //Payment ID
                if (mainScreenState.finalPaymentState == null)
                    Column {
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.PAYMENT_ID_LABEL_TEXT),
                            text = paymentIdLabel,
                            style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.PAYMENT_ID_VALUE_TEXT),
                            text = paymentIdValue,
                            style = SDKTheme.typography.s14Bold.copy(color = Color.White)
                        )
                    }
            }
            if (showExpandableContent) {
                Spacer(modifier = Modifier.size(20.dp))
                if (isExpanded)
                    SDKDivider()
                AnimatedVisibility(visible = isExpanded) {
                    Column {
                        PaymentDetailsContent(
                            actionType = actionType,
                            paymentIdLabel = paymentIdLabel,
                            paymentIdValue = paymentIdValue,
                            paymentDescriptionLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_DESCRIPTION),
                            paymentDescriptionValue = paymentDescriptionValue,
                            merchantAddressLabel = "",
                            merchantAddressValue = merchantAddressValue
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
                SDKButton(
                    modifier = Modifier
                        .height(33.dp)
                        .testTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON),
                    color = Color.White.copy(alpha = 0.1f),
                    textStyle = SDKTheme.typography.s14Normal.copy(color = Color.White),
                    label = if (!isExpanded)
                        getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_SCREEN)
                    else
                        getStringOverride(OverridesKeys.BUTTON_HIDE_DETAILS),
                    isEnabled = true,
                    onClick = {
                        isExpanded = !isExpanded
                    }
                )
            }
        }
    }
}

//@Composable
//internal fun PaymentOverview(
//    showPaymentId: Boolean = true,
//    showPaymentDetailsButton: Boolean = true,
//) {
//    val paymentOptions = LocalPaymentOptions.current
//    var expandPaymentDetailsState by remember { mutableStateOf(false) }
//
//    val paymentIdLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_ID)
//    val paymentIdValue = LocalPaymentOptions.current.paymentInfo.paymentId
//
//    val paymentDescriptionValue = LocalPaymentOptions.current.paymentInfo.paymentDescription
//
//    val merchantAddressValue = null
//
//    ExpandablePaymentOverview(
//        actionType = paymentOptions.actionType,
//        paymentIdLabel = paymentIdLabel,
//        paymentIdValue = paymentIdValue,
//        showPaymentDetailsButton = (
//                paymentOptions.actionType != SDKActionType.Verify ||
//                        paymentDescriptionValue != null ||
//                        merchantAddressValue != null
//                ) && showPaymentDetailsButton,
//        showPaymentId = showPaymentId,
//        isExpanded = expandPaymentDetailsState,
//        onExpand = { expandPaymentDetailsState = !expandPaymentDetailsState }
//    ) {
//        PaymentDetailsContent(
//            actionType = paymentOptions.actionType,
//            paymentIdLabel = paymentIdLabel,
//            paymentIdValue = paymentIdValue,
//            paymentDescriptionLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_DESCRIPTION),
//            paymentDescriptionValue = paymentDescriptionValue,
//            merchantAddressLabel = "",
//            merchantAddressValue = merchantAddressValue
//        )
//    }
//}
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//internal fun ExpandablePaymentOverview(
//    actionType: SDKActionType,
//    paymentIdLabel: String,
//    paymentIdValue: String? = null,
//    showPaymentId: Boolean = true,
//    showPaymentDetailsButton: Boolean = true,
//    isExpanded: Boolean = false,
//    onExpand: () -> Unit,
//    expandableContent: @Composable ColumnScope.() -> Unit,
//) {
//    val mainViewModel = LocalMainViewModel.current
//    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
//    val currentMethod = paymentMethodsViewModel.state.collectAsState().value.currentMethod
//    val payment = mainViewModel.payment
//    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()
//
//    val gradient = arrayOf(
//        0.0f to SDKTheme.colors.primary.copy(alpha = 0.95f),
//        1f to SDKTheme.colors.primary.copy(alpha = 0.80f),
//    )
//
//    val isShowVatInfoLabel = currentMethod?.paymentMethod?.isVatInfo == true
//            || paymentMethods.firstOrNull {
//        if (payment != null)
//            payment.method == it.code
//        else false
//    }?.isVatInfo == true
//
//    Box(
//        modifier = Modifier
//            .background(
//                brush = Brush.linearGradient(
//                    colorStops = gradient
//                ),
//                shape = SDKTheme.shapes.radius12
//            )
//            .fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier
//                .animateContentSize(SnapSpec())
//                .padding(20.dp),
//        ) {
//            LocalPaymentOptions.current.logoImage?.let {
//                Image(
//                    modifier = Modifier
//                        .height(35.dp)
//                        .testTag(TestTagsConstants.LOGO_IMAGE),
//                    alignment = Alignment.TopStart,
//                    bitmap = it.asImageBitmap(),
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                )
//                if (showPaymentId)
//                    Spacer(modifier = Modifier.size(20.dp))
//            }
//            if (actionType != SDKActionType.Verify) {
//                val coinsText =
//                    LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins()
//                val currencyText = LocalPaymentOptions.current.paymentInfo.paymentCurrency
//                Box(
//                    modifier = Modifier
//                        .semantics {
//                            contentDescription = "$coinsText $currencyText"
//                        }
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Text(
//                            modifier = Modifier
//                                .testTag(TestTagsConstants.COINS_VALUE_TEXT),
//                            text = coinsText,
//                            style = SDKTheme.typography.s28Bold.copy(color = Color.White)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            modifier = Modifier
//                                .testTag(TestTagsConstants.CURRENCY_VALUE_TEXT),
//                            text = currencyText,
//                            style = SDKTheme.typography.s16Normal.copy(color = Color.White)
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.size(6.dp))
//                val totalPriceTitle = getStringOverride(OverridesKeys.TITLE_TOTAL_PRICE)
//                val vatIncludedLabel = getStringOverride(OverridesKeys.VAT_INCLUDED)
//                Box(
//                    modifier = Modifier
//                        .semantics {
//                            contentDescription = if (isShowVatInfoLabel)
//                                "$totalPriceTitle $vatIncludedLabel"
//                            else
//                                totalPriceTitle
//                        }
//                ) {
//                    Row {
//                        Text(
//                            modifier = Modifier
//                                .testTag(TestTagsConstants.TOTAL_PRICE_TITLE_TEXT),
//                            text = totalPriceTitle,
//                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
//                        )
//                        Text(
//                            modifier = Modifier
//                                .semantics {
//                                    invisibleToUser()
//                                },
//                            text = " "
//                        )
//                        if (isShowVatInfoLabel)
//                            Text(
//                                modifier = Modifier
//                                    .testTag(TestTagsConstants.VAT_INCLUDED_LABEL_TEXT),
//                                text = vatIncludedLabel,
//                                style = SDKTheme.typography.s14Light.copy(color = Color.White),
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                    }
//                }
//            } else {
//                //Payment ID
//                if (paymentIdValue != null && showPaymentId)
//                    Column {
//                        Text(
//                            modifier = Modifier
//                                .testTag(TestTagsConstants.PAYMENT_ID_LABEL_TEXT),
//                            text = paymentIdLabel,
//                            style = SDKTheme.typography.s12Light.copy(color = Color.White.copy(alpha = 0.6f))
//                        )
//                        Spacer(modifier = Modifier.size(6.dp))
//                        Text(
//                            modifier = Modifier
//                                .testTag(TestTagsConstants.PAYMENT_ID_VALUE_TEXT),
//                            text = paymentIdValue,
//                            style = SDKTheme.typography.s14Bold.copy(color = Color.White)
//                        )
//                    }
//            }
//
//            if (showPaymentDetailsButton) {
//                Spacer(modifier = Modifier.size(20.dp))
//                if (isExpanded) {
//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(1.dp)
//                            .background(Color.White.copy(alpha = 0.1f))
//                    )
//                }
//                AnimatedVisibility(visible = isExpanded) {
//                    Column(
//                        content = {
//                            expandableContent()
//                            Spacer(modifier = Modifier.size(20.dp))
//                        }
//                    )
//                }
//                SDKButton(
//                    modifier = Modifier
//                        .height(33.dp)
//                        .testTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON),
//                    color = Color.White.copy(alpha = 0.1f),
//                    textStyle = SDKTheme.typography.s14Normal.copy(color = Color.White),
//                    label = if (!isExpanded)
//                        getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_SCREEN)
//                    else
//                        getStringOverride(OverridesKeys.BUTTON_HIDE_DETAILS),
//                    isEnabled = true,
//                    onClick = onExpand
//                )
//            }
//        }
//    }
//}
