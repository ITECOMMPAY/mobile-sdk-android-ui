package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.screens.aps.ApsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.clarificationFields.ClarificationFieldsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.customerFields.CustomerFieldsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.loading.LoadingScreen
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.PaymentMethodsScreen
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.presentation.main.screens.result.ResultDeclineScreen
import com.paymentpage.msdk.ui.presentation.main.screens.result.ResultSuccessScreen
import com.paymentpage.msdk.ui.presentation.main.screens.threeDSecure.ThreeDSecureScreen
import com.paymentpage.msdk.ui.presentation.main.screens.tokenize.TokenizeScreen
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods
import com.paymentpage.msdk.ui.views.common.alertDialog.ConfirmAlertDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalAnimationApi::class)
@Suppress("UNUSED_PARAMETER")
@Composable
internal fun MainScreen(
    startRoute: Route,
    actionType: SDKActionType,
    mainScreenNavigator: Navigator,
    delegate: PaymentDelegate,
    onError: (ErrorResult, Boolean) -> Unit,
    onCancel: () -> Unit
) {
    val navController = rememberAnimatedNavController()
    val focusManager = LocalFocusManager.current

    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()
    val savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList()
    val isSaleWithToken = LocalPaymentOptions.current.paymentInfo.token != null
    val isTokenize = actionType == SDKActionType.Tokenize

    val mergedPaymentMethods = remember {
        paymentMethods.mergeUIPaymentMethods(
            isSaleWithToken = isSaleWithToken,
            savedAccounts = savedAccounts
        )
    }

    LaunchedEffect("mainScreenNavigation") {
        mainScreenNavigator.sharedFlow.onEach {
            focusManager.clearFocus()
            navController.navigate(it.getPath())
        }.launchIn(this)
    }
    val mainViewModel = LocalMainViewModel.current

    LaunchedEffect(Unit) {
        mainViewModel.state.onEach {
            when {
                it.error != null -> onError(it.error, true)
                it.isLoading == true ->
                    mainScreenNavigator.navigateTo(Route.Loading)
                it.finalPaymentState != null -> {
                    when (it.finalPaymentState) {
                        is FinalPaymentState.Success -> {
                            if (isTokenize)
                                mainScreenNavigator.navigateTo(Route.SuccessTokenizeDialog)
                            else
                                mainScreenNavigator.navigateTo(Route.SuccessResult)
                        }
                        is FinalPaymentState.Decline -> {
                            if (isTokenize)
                                mainScreenNavigator.navigateTo(Route.DeclineTokenizeDialog)
                            else
                                mainScreenNavigator.navigateTo(Route.DeclineResult)
                        }
                    }
                }
                it.customerFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.CustomerFields)
                it.clarificationFields.isNotEmpty() -> mainScreenNavigator.navigateTo(Route.ClarificationFields)
                it.acsPageState != null -> mainScreenNavigator.navigateTo(Route.AcsPage)
                it.apsPageState != null -> mainScreenNavigator.navigateTo(Route.ApsPage)
            }
        }.collect()
    }
    val lastRoute = mainScreenNavigator.lastRoute

    AnimatedNavHost(
        navController = navController,
        startDestination = lastRoute?.getPath() ?: startRoute.getPath(),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = Route.CustomerFields.getPath()) {
            CustomerFieldsScreen(
                onBack = {
                    navController.navigateUp()
                    navController.navigateUp()
                },
                onCancel = onCancel
            )
        }
        composable(route = Route.ClarificationFields.getPath()) {
            ClarificationFieldsScreen(onCancel = onCancel)
        }
        composable(route = Route.AcsPage.getPath()) {
            ThreeDSecureScreen(onCancel = onCancel)
        }
        composable(route = Route.ApsPage.getPath()) {
            ApsScreen(onCancel = onCancel)
        }
        composable(route = Route.SuccessResult.getPath()) {
            ResultSuccessScreen(onClose = { delegate.onCompleteWithSuccess(it) }, onError = onError)
        }
        composable(route = Route.DeclineResult.getPath()) {
            ResultDeclineScreen(onClose = { delegate.onCompleteWithDecline(it) }, onError = onError)
        }
        composable(route = Route.Loading.getPath()) {
            LoadingScreen(onCancel = onCancel)
        }
        composable(route = Route.PaymentMethods.getPath()) {
            PaymentMethodsScreen(
                uiPaymentMethods = mergedPaymentMethods,
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.Tokenize.getPath()) {
            TokenizeScreen(
                tokenizePaymentMethod = UIPaymentMethod.UITokenizeCardPayPaymentMethod(
                    paymentMethod = paymentMethods.first {
                        it.paymentMethodType == PaymentMethodType.CARD
                    }
                ),
                onCancel = onCancel,
                onError = onError
            )
        }
        composable(route = Route.SuccessTokenizeDialog.getPath()) {
            val payment = mainViewModel.lastState.payment
                ?: throw IllegalStateException("Not found payment in State")
            ConfirmAlertDialog(
                message = { Text(text = getStringOverride(OverridesKeys.TITLE_RESULT_SUCCES_TOKENIZE)) },
                onConfirmButtonClick = { delegate.onCompleteWithSuccess(payment = payment) },
                confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
                onDismissRequest = { delegate.onCompleteWithSuccess(payment = payment) }
            )
        }
        composable(route = Route.DeclineTokenizeDialog.getPath()) {
            val payment = mainViewModel.lastState.payment
                ?: throw IllegalStateException("Not found payment in State")
            ConfirmAlertDialog(
                message = { Text(text = getStringOverride(OverridesKeys.TITLE_RESULT_ERROR_TOKENIZE)) },
                onConfirmButtonClick = { delegate.onCompleteWithDecline(payment = payment) },
                confirmButtonText = getStringOverride(OverridesKeys.BUTTON_OK),
                onDismissRequest = { delegate.onCompleteWithDecline(payment = payment) }
            )
        }
    }
}
