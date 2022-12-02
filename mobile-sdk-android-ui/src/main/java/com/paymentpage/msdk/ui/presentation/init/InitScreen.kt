package com.paymentpage.msdk.ui.presentation.init

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.base.ErrorCode
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.restoreAps
import com.paymentpage.msdk.ui.presentation.main.restorePayment
import com.paymentpage.msdk.ui.theme.SDKTheme
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
    val initViewModel = LocalInitViewModel.current
    val mainViewModel = LocalMainViewModel.current
    LaunchedEffect(Unit) {
        initViewModel.loadInit()
        initViewModel.state.onEach {
            when {
                it.error != null -> onError(it.error, true)
                it.isInitLoaded -> when(PaymentActivity.paymentOptions.actionType) {
                    SDKActionType.Sale -> navigator.navigateTo(Route.Main)
                    SDKActionType.Tokenize -> navigator.navigateTo(Route.Tokenize)
                    else -> navigator.navigateTo(Route.Main)
                }
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
                        if (it.payment.paymentMethodType == PaymentMethodType.APS && it.payment.status?.isFinal == false) {
                            navigator.navigateTo(Route.RestoreAps)
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
    Content(
        actionType = actionType,
        onCancel = onCancel
    )
}


@Composable
private fun Content(
    actionType: SDKActionType,
    onCancel: () -> Unit
) {
    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        title = when(actionType) {
            SDKActionType.Sale -> stringResource(R.string.sale_label)
            SDKActionType.Tokenize -> stringResource(R.string.tokenize_label)
            else -> stringResource(R.string.sale_label)
        },
        notScrollableContent = { Loading() },
        onClose = onCancel,
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
                isVisiblePrivacyPolicy = false,
                isVisibleCookiePolicy = false
            )
        }
    )
}

@Composable
private fun Loading(
    range: IntRange = (1..5),
) {
    ShimmerAnimatedItem(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(10.dp))
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
    range.forEachIndexed { _, index ->
        ShimmerAnimatedItem(
            itemHeight = 50.dp,
            borderRadius = 6.dp
        )
        if (index != range.last)
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
