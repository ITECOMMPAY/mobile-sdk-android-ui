package com.paymentpage.msdk.ui.presentation.main.screens.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.lodaing.DotsLoading

@Composable
internal fun LoadingScreen(onCancel: () -> Unit) {
    BackHandler(true) { }
    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        notScrollableContent = {
            Column(
                modifier = Modifier
                    .background(SDKTheme.colors.backgroundColor)
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                    .fillMaxWidth()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DotsLoading()
                    Spacer(modifier = Modifier.size(35.dp))
                    Text(
                        text = PaymentActivity.stringResourceManager.getStringByKey("title_loading_screen"),
                        style = SDKTheme.typography.s24Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(
                        text = PaymentActivity.stringResourceManager.getStringByKey("sub_title_loading_screen"),
                        style = SDKTheme.typography.s14Normal,
                        textAlign = TextAlign.Center
                    )
                }
                SDKFooter(
                    iconLogo = SDKTheme.images.sdkLogoResId,
                    poweredByText = stringResource(R.string.powered_by_label),
                    isVisibleCookiePolicy = false,
                    isVisiblePrivacyPolicy = false
                )
            }
        },
        onClose = onCancel,
        footerContent = { }
    )

}

@Preview()
@Composable
internal fun LoadingScreenPreview() {
    LoadingScreen(onCancel = {})
}
