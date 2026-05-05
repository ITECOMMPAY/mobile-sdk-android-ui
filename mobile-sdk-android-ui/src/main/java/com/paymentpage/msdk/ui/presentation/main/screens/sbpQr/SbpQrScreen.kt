package com.paymentpage.msdk.ui.presentation.main.screens.sbpQr

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun SbpQrScreen(onCancel: () -> Unit) {
    val qrData = LocalMainViewModel.current.lastState.sbpQrData.orEmpty()
    val qrBitmap = remember(qrData) { generateQrBitmap(qrData, 768) }

    BackHandler(true) { }

    SDKScaffold(
        title = "SBP QR",
        verticalArrangement = Arrangement.Center,
        notScrollableContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (qrBitmap != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = SDKTheme.colors.cardBackground,
                                shape = SDKTheme.shapes.radius20
                            )
                            .padding(16.dp),
                    ) {
                        Image(
                            bitmap = qrBitmap.asImageBitmap(),
                            contentDescription = "SBP QR",
                            modifier = Modifier
                                .size(260.dp)
                                .testTag("SBP_QR_IMAGE")
                        )
                    }
                }

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("SBP_QR_LINK_TEXT"),
                    text = qrData,
                    style = SDKTheme.typography.s14Normal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(24.dp))
                SDKFooter()
            }
        },
        onClose = onCancel,
        showCloseButton = false,
    )
}

private fun generateQrBitmap(content: String, size: Int): Bitmap? {
    if (content.isBlank()) return null

    return runCatching {
        val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }

        bitmap
    }.getOrNull()
}
