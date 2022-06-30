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
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme


@Composable
internal fun SDKScaffold(
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
                SDKTopBar(
                    title = title,
                    arrowIcon = null
                )
                notScrollableContent()
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    scrollableContent()
                    if (!isFloatingFooter) {
                        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
                        SDKFooter(
                            iconLogo = R.drawable.ic_sdk_logo,
                            poweredByText = stringResource(R.string.powered_by_label)
                        )
                    }
                }
                if (isFloatingFooter) {
                    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))
                    SDKFooter(
                        iconLogo = R.drawable.ic_sdk_logo,
                        poweredByText = stringResource(R.string.powered_by_label)
                    )
                }
            }
        }
    )
}