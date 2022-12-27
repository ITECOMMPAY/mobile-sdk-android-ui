package com.paymentpage.msdk.ui.presentation.main.screens.result.views.decline

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.FinalPaymentState
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.ResultTableInfo
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.animation.VerticalSlideFadeAnimation
import com.paymentpage.msdk.ui.presentation.main.tryAgain
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.button.SDKButton
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import kotlinx.coroutines.delay

@Composable
internal fun ResultDeclineSaleContent(
    onClose: (Payment) -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val payment =
        mainViewModel.payment ?: throw IllegalStateException("Not found payment in State")
    val isTryAgain =
        (mainViewModel.lastState.finalPaymentState as? FinalPaymentState.Decline)?.isTryAgain ?: false
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately
            targetState = true
        }
    }

    var spacerWeightState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(800)
        spacerWeightState = true
    }

    val weight by animateFloatAsState(
        targetValue = if (spacerWeightState) 0.01f else 0.4f,
        animationSpec = keyframes {
            durationMillis = 1000
            0.4f at 0 with EaseInOutExpo
            0.01f at 1000
        }
    )

    SDKScaffold(
        modifier = Modifier.fillMaxSize(),
        scrollableContent = {
            Row(
                modifier = Modifier
                    .animateContentSize()
                    .weight(weight)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    duration = 700,
                    initialOffsetYRatio = 0.0f
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = SDKTheme.images.errorLogo),
                            contentDescription = null
                        )

                    }
                }

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 600,
                    duration = 400,
                    initialOffsetYRatio = 0.3f,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            text = getStringOverride(OverridesKeys.TITLE_RESULT_ERROR_PAYMENT),
                            style = SDKTheme.typography.s24Bold,
                            textAlign = TextAlign.Center
                        )
                        if (!payment.paymentMassage.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.size(5.dp))
                            Text(
                                text = payment.paymentMassage!!,
                                style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.errorTextColor),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 1000,
                    duration = 500,
                    initialOffsetYRatio = 0.3f
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(15.dp))
                        PaymentOverview(showPaymentDetailsButton = false)
                    }
                }

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 1100,
                    duration = 500,
                    initialOffsetYRatio = 0.3f
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(15.dp))
                        ResultTableInfo(onError)
                    }
                }

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 1200,
                    duration = 500,
                    initialOffsetYRatio = 0.3f
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(15.dp))
                        if (!isTryAgain)
                            SDKButton(
                                label = getStringOverride(OverridesKeys.BUTTON_CLOSE),
                                isEnabled = true
                            ) { onClose(payment) }
                        else
                            SDKButton(
                                label = getStringOverride(OverridesKeys.BUTTON_TRY_AGAIN),
                                isEnabled = true
                            ) {
                                paymentMethodsViewModel.setCurrentMethod(null)
                                mainViewModel.tryAgain()
                            }
                    }
                }

                VerticalSlideFadeAnimation(
                    visibleState = visibleState,
                    delay = 1300,
                    duration = 500,
                    initialOffsetYRatio = 0.3f
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(15.dp))
                        SDKFooter()
                        Spacer(modifier = Modifier.size(25.dp))
                    }
                }

            }
        },
        onClose = { onClose(payment) },
        showCloseButton = isTryAgain
    )
}