package com.paymentpage.msdk.ui.presentation.main.screens.loading

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.animation.VerticalSlideFadeAnimation
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.lodaing.DotsLoading

@Composable
internal fun LoadingScreen(onCancel: () -> Unit) {

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately
            targetState = true
        }
    }

    val isPreview = LocalInspectionMode.current

    BackHandler(true) { }

    SDKScaffold(
        verticalArrangement = Arrangement.Center,
        notScrollableContent = {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedVisibility(
                    visibleState = state,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                ) {
                    DotsLoading()
                }
                Spacer(modifier = Modifier.size(35.dp))
                VerticalSlideFadeAnimation(
                    visibleState = state,
                    duration = 500,
                    delay = 500
                ) {
                    Text(
                        text = if (isPreview)
                            "Just a moment"
                        else
                            getStringOverride(OverridesKeys.TITLE_LOADING_SCREEN),
                        style = SDKTheme.typography.s24Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
                VerticalSlideFadeAnimation(
                    visibleState = state,
                    duration = 500,
                    delay = 800,
                    initialOffsetYRatio = 0.5f
                ) {
                    Text(
                        text = if (isPreview)
                            "Checking your operation status..."
                        else
                            getStringOverride(OverridesKeys.SUB_TITLE_LOADING_SCREEN),
                        style = SDKTheme.typography.s14Normal,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.size(35.dp))
                AnimatedVisibility(
                    visibleState = state,
                    enter = fadeIn(
                        animationSpec = tween(
                            delayMillis = 1300,
                            durationMillis = 500
                        )
                    )
                ) {
                    ClickableText(
                        style = SDKTheme.typography.s14Normal.copy(
                            color = SDKTheme.colors.mediumGrey,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline
                        ),
                        text = AnnotatedString(
                            if (isPreview)
                                "Cancel Payment"
                            else
                                getStringOverride(OverridesKeys.TITLE_CANCEL_PAYMENT)
                        ),
                        onClick = {
                            onCancel()
                        }
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
            if (isPreview)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Powered by",
                    style = TextStyle(textAlign = TextAlign.Center)
                )
            else
                SDKFooter(
                    isVisibleCookiePolicy = false,
                    isVisiblePrivacyPolicy = false
                )
            Spacer(modifier = Modifier.size(25.dp))
        },
        onClose = onCancel,
        showCloseButton = false
    )
}

@Preview
@Composable
internal fun LoadingScreenPreview() {
    LoadingScreen(onCancel = {})
}
