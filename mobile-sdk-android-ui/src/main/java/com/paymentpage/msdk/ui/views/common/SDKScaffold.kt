package com.paymentpage.msdk.ui.views.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.core.MSDKCoreSessionConfig
import com.paymentpage.msdk.core.domain.entities.PaymentInfo
import com.paymentpage.msdk.core.utils.Duration
import com.paymentpage.msdk.ui.SDKCommonProvider
import com.paymentpage.msdk.ui.SDKPaymentOptions
import com.paymentpage.msdk.ui.TestTagsConstants
import com.paymentpage.msdk.ui.base.Constants
import com.paymentpage.msdk.ui.theme.SDKTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun SDKScaffold(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    horizontalPadding: Dp = 16.dp,
    title: String? = null,
    showCloseButton: Boolean = true,
    notScrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    scrollableContent: (@Composable ColumnScope.() -> Unit)? = null,
    onClose: () -> Unit,
    onBack: (() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClose
                )
                .semantics {
                    invisibleToUser()
                }
                .testTag(TestTagsConstants.FREE_SPACE_CLOSE_PAYMENT_BUTTON)
        )
        Column(
            modifier = Modifier
                .background(
                    color = SDKTheme.colors.background,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.9f) //Height of bottom sheet
                .fillMaxWidth()
                .padding(top = 25.dp)
                .padding(horizontal = horizontalPadding),
            content = {
                if (title != null || showCloseButton || onBack != null) {
                    SDKTopBar(
                        title = title,
                        showCloseButton = showCloseButton,
                        onClose = onClose,
                        onBack = onBack
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                }
                if (notScrollableContent != null) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth(),
                        content = notScrollableContent,
                        verticalArrangement = verticalArrangement,
                        horizontalAlignment = horizontalAlignment,
                    )
                }
                if (scrollableContent != null) {
                    Column(
                        modifier = modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        content = scrollableContent,
                        verticalArrangement = verticalArrangement,
                        horizontalAlignment = horizontalAlignment,
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun SDKScaffoldPreview() {
    SDKTheme {
        val options = SDKPaymentOptions(
            paymentInfo = PaymentInfo(
                projectId = 124124,
                paymentId = "ewfew",
                paymentAmount = 0L,
                paymentCurrency = "USD"
            )
        )

        val config = MSDKCoreSessionConfig.mockFullSuccessFlow(
            duration = Duration.millis(Constants.THREE_D_SECURE_REDIRECT_DURATION)
        )

        val msdkSession = MSDKCoreSession(config)

        SDKCommonProvider(options, msdkSession) {
            SDKScaffold(
                onClose = {},
                title = "Payment methods"
            )
        }
    }
}