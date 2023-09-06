package com.paymentpage.msdk.ui.presentation.init

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.ui.LocalInitViewModel
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.LocalMsdkSession
import com.paymentpage.msdk.ui.LocalPaymentMethodsViewModel
import com.paymentpage.msdk.ui.LocalPaymentOptions
import com.paymentpage.msdk.ui.SDKActionType
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.restoreAps
import com.paymentpage.msdk.ui.presentation.main.restorePayment
import com.paymentpage.msdk.ui.presentation.main.screens.paymentMethods.models.UIPaymentMethod
import com.paymentpage.msdk.ui.utils.extensions.core.mergeUIPaymentMethods
import com.paymentpage.msdk.ui.utils.extensions.stringResourceIdFromStringName
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.shimmer.ShimmerAnimatedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(
    actionType: SDKActionType,
    navigator: Navigator,
    onCancel: () -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    BackHandler(true) { onCancel() }

    setupStateListener(actionType = actionType, navigator = navigator, onError = onError)
    Content(
        actionType = actionType,
        onCancel = onCancel
    )
}

@Composable
private fun setupStateListener(
    actionType: SDKActionType,
    navigator: Navigator,
    onError: (ErrorResult, Boolean) -> Unit,
) {
    val initViewModel = LocalInitViewModel.current
    val mainViewModel = LocalMainViewModel.current
    val paymentMethodsViewModel = LocalPaymentMethodsViewModel.current
    val paymentMethods = LocalMsdkSession.current.getPaymentMethods() ?: emptyList()
    val savedAccounts = LocalMsdkSession.current.getSavedAccounts() ?: emptyList()

    if (paymentMethods.isNotEmpty())
        paymentMethodsViewModel.setPaymentMethods(
            paymentMethods.mergeUIPaymentMethods(
                actionType = actionType,
                savedAccounts = savedAccounts
            )
        )

    LaunchedEffect(Unit) {
        initViewModel.loadInit()
        initViewModel.state.onEach {
            when {
                it.error != null -> onError(it.error, true)
                it.isInitLoaded -> navigator.navigateTo(Route.Main)
                it.payment != null -> {
                    val paymentMethod =
                        it.paymentMethods?.find { paymentMethod -> paymentMethod.code == it.payment.method }
                    if (paymentMethod == null) {
                        onError(
                            ErrorResult(
                                code = ErrorCode.PAYMENT_METHOD_NOT_AVAILABLE,
                                "Payment method ${it.payment.method} does not found"
                            ), true
                        )
                    } else {
                        if (it.payment.paymentMethodType == PaymentMethodType.APS) {
                            navigator.navigateTo(Route.RestoreAps)
                            paymentMethodsViewModel.setCurrentMethod(
                                UIPaymentMethod.UIApsPaymentMethod(
                                    index = 0,
                                    title = paymentMethod.name ?: paymentMethod.code,
                                    paymentMethod = paymentMethod
                                )
                            )
                            mainViewModel.restoreAps(apsMethod = paymentMethod)
                        } else {
                            navigator.navigateTo(Route.Restore)
                            mainViewModel.restorePayment()
                        }
                    }
                }

            }
        }.collect()
    }
}


@Composable
private fun Content(
    actionType: SDKActionType,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val paymentOptions = LocalPaymentOptions.current
    val languageCode = paymentOptions.paymentInfo.languageCode

    val screenTitleResourceId = when (actionType) {
        SDKActionType.Sale -> context.stringResourceIdFromStringName("sale_label", languageCode)
        SDKActionType.Tokenize -> context.stringResourceIdFromStringName("tokenize_label", languageCode)
        SDKActionType.Verify -> context.stringResourceIdFromStringName("verify_label", languageCode)
        SDKActionType.Auth -> context.stringResourceIdFromStringName("sale_label", languageCode)
    }

    SDKScaffold(
        title = stringResource(id = screenTitleResourceId),
        notScrollableContent = {
            Loading()
            Spacer(modifier = Modifier.size(5.dp))
            SDKFooter(
                isVisiblePrivacyPolicy = false,
                isVisibleCookiePolicy = false
            )
            Spacer(modifier = Modifier.size(25.dp))
        },
        onClose = onCancel
    )
}

@Composable
private fun Loading(
    range: IntRange = (1..5),
) {
    ShimmerAnimatedItem(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(10.dp))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
    range.forEach { _ ->
        ShimmerAnimatedItem(
            itemHeight = 50.dp,
            borderRadius = 6.dp
        )
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoadingPreview() {
    Content(
        actionType = SDKActionType.Sale,
        onCancel = {}
    )
}
