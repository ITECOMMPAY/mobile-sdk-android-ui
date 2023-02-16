package com.paymentpage.msdk.ui.views.card

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.cardScanning.CardScanningActivityContract
import com.paymentpage.msdk.ui.theme.SDKTheme

@Composable
internal fun CardScanningItem(
    modifier: Modifier = Modifier,
    onScanningResult: (CardScanningActivityContract.Result) -> Unit,
) {
    val guideColor = SDKTheme.colors.brand
    val launcher =
        rememberLauncherForActivityResult(contract = CardScanningActivityContract()) { result ->
            onScanningResult(result)
        }
    Box(
        modifier = modifier
            .clip(SDKTheme.shapes.radius6)
            .background(SDKTheme.colors.panelBackgroundColor)
            .border(
                width = 1.dp,
                color = SDKTheme.colors.borderColor,
                shape = SDKTheme.shapes.radius6
            )
            .clickable {
                launcher.launch(CardScanningActivityContract.Config(guideColor.toArgb()))
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = R.drawable.card_scanning_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }

}