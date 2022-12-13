package com.paymentpage.msdk.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.msdk.core.MSDKCoreSession
import com.paymentpage.msdk.ui.presentation.init.InitViewModel
import com.paymentpage.msdk.ui.presentation.main.MainViewModel
import com.paymentpage.msdk.ui.utils.viewModelFactory

internal val LocalPaymentOptions =
    compositionLocalOf<SDKPaymentOptions> { error("No PaymentOptions found!") }

internal val LocalMsdkSession =
    compositionLocalOf<MSDKCoreSession> { error("No MSDKCoreSession found!") }

internal val LocalMainViewModel =
    compositionLocalOf<MainViewModel> { error("No MainViewModel found!") }

internal val LocalInitViewModel =
    compositionLocalOf<InitViewModel> { error("No InitViewModel found!") }


@Composable
internal fun SDKCommonProvider(
    paymentOptions: SDKPaymentOptions,
    msdkSession: MSDKCoreSession,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalPaymentOptions provides paymentOptions,
        LocalMsdkSession provides msdkSession,
        LocalMainViewModel provides viewModel(
            factory = viewModelFactory {
                MainViewModel(
                    cardRemoveInteractor = msdkSession.getCardRemoveInteractor(),
                    payInteractor = msdkSession.getPayInteractor()
                )
            }
        ),
        LocalInitViewModel provides viewModel(
            factory = viewModelFactory {
                InitViewModel(
                    initInteractor = msdkSession.getInitInteractor(),
                    paymentOptions = paymentOptions,
                )
            }
        ),
        content = content
    )
}