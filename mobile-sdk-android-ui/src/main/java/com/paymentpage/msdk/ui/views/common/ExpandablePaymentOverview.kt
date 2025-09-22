package com.paymentpage.msdk.ui.views.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalMsdkSession
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.base.Constants.DEFAULT_LANGUAGE
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.detail.PaymentDetailsContent
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.table.RecurrentInfoTable
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.SohneBreitFamily
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.RecurrentTypeUI
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.isShowRecurringUI
import com.paymentpage.msdk.ui.utils.extensions.core.typeUI
import com.paymentpage.msdk.ui.utils.extensions.toCurrencySign
import com.paymentpage.msdk.ui.utils.extensions.toLanguageCode


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ExpandablePaymentOverview(
    actionType: SDKActionType,
    expandable: Boolean,
) {
    val paymentOptions = LocalPaymentOptions.current

    val mainViewModel = LocalMainViewModel.current
    val mainScreenState = mainViewModel.state.collectAsState().value

    val paymentIdLabel = getStringOverride(OverridesKeys.TITLE_PAYMENT_ID)
    val paymentIdValue = if (actionType != SDKActionType.Verify)
        paymentOptions.paymentInfo.paymentId else null

    val paymentDescriptionLabel =
        getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_DESCRIPTION)
    val paymentDescriptionValue = paymentOptions.paymentInfo.paymentDescription

    val showExpandableContent =
        expandable && (paymentIdValue != null || paymentDescriptionValue != null)
    var isDividerVisible by remember { mutableStateOf(true) }

    //expand state
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(SDKTheme.colors.primary, shape = SDKTheme.shapes.radius20)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.animateContentSize(SnapSpec())
        ) {
            Image(
                painter = painterResource(R.drawable.card_payment_overview_background),
                contentDescription = "background",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(20.dp),
            ) {

                // HEADER. Logo + language indicator
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
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
                    }

                    Spacer(Modifier.weight(1f))

                    val language = paymentOptions.paymentInfo.languageCode ?: DEFAULT_LANGUAGE

                    Row(
                        horizontalArrangement = Arrangement.End,
                    ) {
                        mapLanguageToIcon(language)?.let {
                            Image(
                                painter = it,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 6.dp)
                                    .testTag(TestTagsConstants.LANGUAGE_ICON),
                                contentDescription = null,
                            )
                        }

                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .testTag(TestTagsConstants.LANGUAGE_VALUE_TEXT),
                            text = language.toLanguageCode(),
                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                        )
                    }
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

                // Payment ID section
                when (actionType) {
                    SDKActionType.Verify -> {
                        if (mainScreenState.finalPaymentState == null) {
                            val paymentIdValueFinal = paymentOptions.paymentInfo.paymentId
                            Column {
                                Text(
                                    modifier = Modifier
                                        .testTag(TestTagsConstants.PAYMENT_ID_LABEL_TEXT),
                                    text = paymentIdLabel,
                                    style = SDKTheme.typography.s12Light.copy(
                                        color = Color.White.copy(
                                            alpha = 0.6f
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.size(6.dp))
                                Text(
                                    modifier = Modifier
                                        .testTag(TestTagsConstants.PAYMENT_ID_VALUE_TEXT),
                                    text = paymentIdValueFinal,
                                    style = SDKTheme.typography.s14Bold.copy(color = Color.White)
                                )
                            }
                        }
                    }

                    else -> {
                        val coinsText =
                            LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins()
                        val currencyText = paymentOptions.paymentInfo.paymentCurrency.toCurrencySign()

                        Box(
                            modifier = Modifier
                                .semantics {
                                    contentDescription = "$coinsText $currencyText"
                                }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier
                                        .testTag(TestTagsConstants.CURRENCY_VALUE_TEXT),
                                    text = currencyText,
                                    fontFamily = SohneBreitFamily,
                                    style = SDKTheme.typography.s36Medium.copy(color = Color.White)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    modifier = Modifier
                                        .testTag(TestTagsConstants.COINS_VALUE_TEXT),
                                    text = coinsText,
                                    fontFamily = SohneBreitFamily,
                                    style = SDKTheme.typography.s36Medium.copy(color = Color.White)
                                )
                            }
                        }
                    }
                }

                if (showExpandableContent) {
                    Spacer(modifier = Modifier.size(20.dp))
                    if (isExpanded)
                        SDKDivider()
                    AnimatedVisibility(visible = isExpanded) {
                        Column {
                            PaymentDetailsContent(
                                paymentIdLabel = paymentIdLabel,
                                paymentIdValue = paymentIdValue,
                                paymentDescriptionLabel = paymentDescriptionLabel,
                                paymentDescriptionValue = paymentDescriptionValue,
                            )
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    }

                    val paymentDetailsLabel = when (isExpanded) {
                        true -> getStringOverride(OverridesKeys.BUTTON_HIDE_DETAILS)
                        else -> getStringOverride(OverridesKeys.TITLE_PAYMENT_INFORMATION_SCREEN)
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { isExpanded = !isExpanded }
                    ) {
                        Text(
                            modifier = Modifier
                                .testTag(TestTagsConstants.PAYMENT_DETAILS_LABEL_TEXT)
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            text = paymentDetailsLabel,
                            fontFamily = SohneBreitFamily,
                            style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                        )

                        val rotationDegree = when (isExpanded) {
                            true -> 180f
                            else -> 0f
                        }

                        val rotation = remember { Animatable(initialValue = 0f) }

                        LaunchedEffect(isExpanded) {
                            rotation.animateTo(
                                targetValue = rotationDegree,
                                animationSpec = tween(durationMillis = 500),
                            )
                        }

                        Image(
                            painter = painterResource(R.drawable.ic_payment_details_expand),
                            modifier = Modifier
                                .testTag(TestTagsConstants.PAYMENT_DETAILS_BUTTON)
                                .rotate(rotation.value)
                                .clickable { isExpanded = !isExpanded },
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun mapLanguageToIcon(code: String) = when (code) {
    "EN" -> painterResource(R.drawable.ic_en)
    "DE" -> painterResource(R.drawable.ic_de)
    "FR" -> painterResource(R.drawable.ic_fr)
    "IT" -> painterResource(R.drawable.ic_it)
    "ES" -> painterResource(R.drawable.ic_es)
    "HU" -> painterResource(R.drawable.ic_hu)
    else -> null
}