package com.paymentpage.msdk.ui.presentation.main.screens.sbpQr

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.OverridesKeys.BUTTON_PAY
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.animation.VerticalSlideFadeAnimation
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.SDKButton
import com.paymentpage.msdk.ui.views.common.ExpandablePaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.common.SDKScaffoldPreview
import kotlinx.coroutines.delay

private const val TABLET_MIN_SMALLEST_WIDTH_DP = 600

@Composable
internal fun SbpQrScreen(
    actionType: SDKActionType,
    onLinkClicked: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val smallestScreenWidthDp = LocalConfiguration.current.smallestScreenWidthDp

    val lastState = mainViewModel.lastState
    val method = paymentMethodsViewModel.resolveSbpMethod(mainViewModel.payment?.method)
    val qrData = lastState.sbpQrData.orEmpty()
    val isTablet = remember(smallestScreenWidthDp) {
        derivedStateOf { smallestScreenWidthDp >= TABLET_MIN_SMALLEST_WIDTH_DP }
    }
    val qrBitmap = remember(qrData, isTablet) {
        if (isTablet.value) generateQrBitmap(qrData, 768) else null
    }

    if (!isTablet.value) {
        LaunchedEffect(qrData) {
            delay(700)
            onLinkClicked(qrData)
        }
    }

    BackHandler(true) { }

    SDKScaffold(
        title = method?.title,
        verticalArrangement = Arrangement.Center,
        horizontalPadding = 0.dp,
        scrollableContent = {
            SbpQrContent(
                actionType = actionType,
                qrData = qrData,
                qrBitmap = qrBitmap,
                isTablet = isTablet.value,
                onLinkClicked = onLinkClicked,
            )
        },
        onClose = onCancel,
    )
}

@Composable
internal fun SbpQrContent(
    actionType: SDKActionType,
    qrData: String,
    qrBitmap: Bitmap?,
    isTablet: Boolean,
    modifier: Modifier = Modifier,
    onLinkClicked: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val visibleState = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately
                targetState = true
            }
        }

        //remove payment overview block if logo does not exist when verify
        if (actionType != SDKActionType.Verify || LocalPaymentOptions.current.logoImage != null) {
            VerticalSlideFadeAnimation(
                visibleState = visibleState,
                delay = 300,
                duration = 200,
                initialOffsetYRatio = 0.3f
            ) {
                ExpandablePaymentOverview(
                    actionType = actionType,
                    expandable = false
                )
            }

            Spacer(modifier = Modifier.size(28.dp))
        }

        if (isTablet) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (qrBitmap != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = SDKTheme.colors.cardBackground,
                                shape = SDKTheme.shapes.radius20
                            )
                            .padding(18.dp),
                    ) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "SBP QR",
                            modifier = Modifier
                                .size(240.dp)
                                .testTag(TestTagsConstants.SBP_QR_IMAGE)
                        )
                    }
                }

                Spacer(modifier = Modifier.size(14.dp))

                SbpQrLinkText(
                    qrData = qrData,
                    onLinkClicked = onLinkClicked
                )

                Spacer(modifier = Modifier.size(14.dp))

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 350,
                    duration = 200,
                    initialOffsetYRatio = 0.3f
                ) {
                    SDKButton(
                        modifier = Modifier.testTag(TestTagsConstants.PAY_BUTTON),
                        label = getStringOverride(BUTTON_PAY),
                        isEnabled = true
                    ) { onLinkClicked(qrData) }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SbpQrLinkText(
                    qrData = qrData,
                    onLinkClicked = onLinkClicked
                )
            }
        }

        VerticalSlideFadeAnimation(
            visibleState = visibleState,
            delay = 400,
            duration = 200,
            initialOffsetYRatio = 0.3f
        ) {
            Column {
                Spacer(modifier = Modifier.size(15.dp))
                SDKFooter()
                Spacer(modifier = Modifier.size(25.dp))
            }
        }
    }
}

@Composable
private fun SbpQrLinkText(
    qrData: String,
    onLinkClicked: (String) -> Unit,
) {
    Text(
        modifier = Modifier
            .testTag(TestTagsConstants.SBP_QR_LINK_TEXT)
            .clickable(enabled = qrData.isNotBlank()) {
                onLinkClicked(qrData)
            },
        text = qrData,
        style = SDKTheme.typography.s14Normal.copy(
            color = SDKTheme.colors.link,
            textDecoration = TextDecoration.Underline
        ),
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun SbpQrContentPreview() {
    val qrData = "https://example.com/sbp/qr/payment?id=1234567890"
    val qrBitmap = remember { generateQrBitmap(qrData, 768) }

    SDKScaffoldPreview(
        title = "SBP QR",
        verticalArrangement = Arrangement.Center,
        horizontalPadding = 0.dp,
        notScrollableContent = {
            SbpQrContent(
                actionType = SDKActionType.Sale,
                qrData = qrData,
                qrBitmap = qrBitmap,
                isTablet = true,
            )
        }
    )
}
