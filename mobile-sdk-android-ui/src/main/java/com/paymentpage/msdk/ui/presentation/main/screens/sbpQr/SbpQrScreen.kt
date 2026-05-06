package com.paymentpage.msdk.ui.presentation.main.screens.sbpQr

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.common.SDKScaffoldPreview

@Composable
internal fun SbpQrScreen(onCancel: () -> Unit) {
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
                qrBitmap = qrBitmap,
                title = "Для оплаты",
                description = "Отсканируйте QR-код в мобильном приложении банка или штатной камерой телефона",
            )
        },
        onClose = onCancel,
    )
}

@Composable
internal fun SbpQrContent(
    qrBitmap: Bitmap?,
    title: String,
    description: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFDF9FA),
                        Color(0xFFF8F8FA),
                        Color(0xFFF5F5F7),
                    )
                )
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x66CEC2FF),
                        Color(0x00CEC2FF),
                    ),
                    radius = 720f,
                    center = androidx.compose.ui.geometry.Offset(820f, 120f)
                )
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x66FF8A8A),
                        Color(0x00FF8A8A),
                    )
                    ,
                    radius = 760f,
                    center = androidx.compose.ui.geometry.Offset(-80f, 980f)
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.35f))

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

            Spacer(modifier = Modifier.size(28.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SBP_QR_TITLE_TEXT"),
                text = title,
                style = SDKTheme.typography.s28Bold.copy(color = SDKTheme.colors.textPrimary),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SBP_QR_DESCRIPTION_TEXT"),
                text = description,
                style = SDKTheme.typography.s20SemiBold.copy(color = SDKTheme.colors.textPrimary),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.65f))
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
                qrBitmap = qrBitmap,
                title = "Для оплаты",
                description = "Отсканируйте QR-код в мобильном приложении банка или штатной камерой телефона",
            )
        }
    )
}
