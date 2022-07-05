package com.ecommpay.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.core.annotatedString


@Composable
internal fun Scaffold(
    title: String = "",
    isFloatingFooter: Boolean = false,
    notScrollableContent: @Composable () -> Unit = {},
    scrollableContent: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SDKTheme.colors.backgroundPaymentMethods)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
            .fillMaxWidth(),
        content = {
            Column(
                modifier = Modifier.padding(SDKTheme.dimensions.paddingDp20),
                horizontalAlignment = Alignment.Start
            ) {
                TopBar(
                    title = title,
                    arrowIcon = null
                )
                notScrollableContent()
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    scrollableContent()
                    if (!isFloatingFooter) {
                        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
                        Footer(
                            iconLogo = SDKTheme.images.sdkLogoResId,
                            poweredByText = stringResource(R.string.powered_by_label),
                            privacyPolicy = PaymentActivity.stringResourceManager.policy.footerPrivacyPolicy?.annotatedString()
                        )
                    }
                }
                if (isFloatingFooter) {
                    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
                    Footer(
                        iconLogo = SDKTheme.images.sdkLogoResId,
                        poweredByText = stringResource(R.string.powered_by_label)
                    )
                }
            }
        }
    )
}