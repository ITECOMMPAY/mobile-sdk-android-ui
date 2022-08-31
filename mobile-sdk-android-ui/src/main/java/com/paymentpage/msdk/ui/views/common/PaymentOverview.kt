package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.getColorMatrix

@Composable
internal fun PaymentOverview(
    alpha: Float = 1f
) {
    val mainViewModel = LocalMainViewModel.current
    val currentMethod = mainViewModel.state.collectAsState().value.currentMethod

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (LocalPaymentOptions.current.logoImage != null) 150.dp else 95.dp)
            .alpha(alpha)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_lines_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.colorMatrix(SDKTheme.colors.brand.getColorMatrix(1f)),
            modifier = Modifier.clip(SDKTheme.shapes.radius12)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            LocalPaymentOptions.current.logoImage?.let {
                Image(
                    alignment = Alignment.TopStart,
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomStart),
            ) {
                Row {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = LocalPaymentOptions.current.paymentInfo.paymentAmount.amountToCoins(),
                        style = SDKTheme.typography.s28Bold.copy(color = Color.White)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(8.dp)
                            .alignByBaseline()
                    )
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = LocalPaymentOptions.current.paymentInfo.paymentCurrency,
                        style = SDKTheme.typography.s16Normal.copy(color = Color.White)
                    )
                }
                //Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = getStringOverride("title_total_price"),
                        style = SDKTheme.typography.s14SemiBold.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (currentMethod?.paymentMethod?.isVatInfo == true)
                        Text(
                            text = getStringOverride("vat_included"),
                            style = SDKTheme.typography.s14Light.copy(color = Color.White)
                        )
                }
            }
        }
    }
}
