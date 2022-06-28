package com.ecommpay.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.theme.SDKTheme


@Composable
internal fun SDKScaffold(
    title: String = "",
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(SDKTheme.colors.backgroundPaymentMethods),
        content = {
            Column(
                modifier = Modifier.padding(SDKTheme.dimensions.paddingDp20),
                horizontalAlignment = Alignment.Start
            ) {
                SDKTopBar(
                    title = title,
                    arrowIcon = null
                )
                content()
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))

                SDKFooter(
                    iconLogo = R.drawable.sdk_logo,
                    poweredByText = stringResource(R.string.powered_by_label)
                )
            }
        }
    )
}