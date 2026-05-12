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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.animation.VerticalSlideFadeAnimation
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.button.SDKButton
import com.paymentpage.msdk.ui.views.common.ExpandablePaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.common.SDKScaffoldPreview

@Composable
internal fun SbpQrScreen(
    actionType: SDKActionType,
    onLinkClicked: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current

    val lastState = mainViewModel.lastState
    val method =
        paymentMethodsViewModel.lastState.currentMethod as UIPaymentMethod.UISbpQrPaymentMethod
    val qrData = lastState.sbpQrData.orEmpty()
    val qrBitmap = remember(qrData) { generateQrBitmap(qrData, 768) }

    BackHandler(true) { }

    SDKScaffold(
        title = method.title,
        verticalArrangement = Arrangement.Center,
        horizontalPadding = 0.dp,
        notScrollableContent = {
            SbpQrContent(
                actionType = actionType,
                qrData = qrData,
                qrBitmap = qrBitmap,
                title = "Отсканируйте QR-код указанный ниже, чтобы продолжить оплату",
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
    title: String,
    onLinkClicked: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val visibleState = remember {
                MutableTransitionState(false).apply {
                    // Start the animation immediately
                    targetState = true
                }
            }

            //remove payment overview block if logo does not exist when verify
            if (actionType != SDKActionType.Verify || LocalPaymentOptions.current.logoImage != null)
                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 1000,
                    duration = 500,
                    initialOffsetYRatio = 0.3f
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(24.dp))
                        ExpandablePaymentOverview(
                            actionType = actionType,
                            expandable = false
                        )
                    }

                    Spacer(modifier = Modifier.size(28.dp))
                }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SBP_QR_TITLE_TEXT"),
                text = title,
                style = SDKTheme.typography.s20SemiBold.copy(color = SDKTheme.colors.textPrimary),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(28.dp))

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
                            .testTag("SBP_QR_IMAGE")
                    )
                }
            }

            Spacer(modifier = Modifier.size(14.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag("SBP_QR_LINK_TEXT")
                    .clickable(enabled = qrData.isNotBlank()) {
                        onLinkClicked(qrData)
                    },
                text = "Или перейдите по ссылке",
                style = SDKTheme.typography.s14Normal.copy(
                    color = SDKTheme.colors.link,
                    textDecoration = TextDecoration.Underline
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(28.dp))

            VerticalSlideFadeAnimation(
                visibleState = visibleState,
                delay = 1200,
                duration = 500,
                initialOffsetYRatio = 0.3f
            ) {
                Column {
                    Spacer(modifier = Modifier.size(24.dp))
                    SDKButton(
                        modifier = Modifier
                            .testTag(TestTagsConstants.PAY_BUTTON),
                        label = "Оплатить",
                        isEnabled = true
                    ) { onLinkClicked(qrData) }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
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
                title = "Отсканируйте QR-код указанный ниже, чтобы продолжить оплату",
            )
        }
    )
}
