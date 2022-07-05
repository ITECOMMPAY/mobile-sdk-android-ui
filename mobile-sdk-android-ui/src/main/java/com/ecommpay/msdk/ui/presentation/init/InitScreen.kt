package com.ecommpay.msdk.ui.presentation.init

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentDelegate
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Navigator
import com.ecommpay.msdk.ui.navigation.Route
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.viewModelFactory
import com.ecommpay.msdk.ui.views.common.Scaffold
import com.ecommpay.msdk.ui.views.shimmer.ShimmerAnimatedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(
    viewModel: InitViewModel = viewModel(
        factory = viewModelFactory {
            InitViewModel(
                initInteractor = PaymentActivity.msdkSession.getInitInteractor(),
                paymentInfo = PaymentActivity.paymentOptions.paymentInfo
                    ?: throw IllegalAccessException("Payment Info can not be null")
            )
        }
    ),
    navigator: Navigator,
    delegate: PaymentDelegate
) {
    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when {
                it.error != null -> delegate.onError(it.error.code, it.error.message)
                it.isInitLoaded -> navigator.navigateTo(Route.Main)
            }
        }.collect()
    }
    Content()
}


@Composable
private fun Content() {
    Scaffold(
        title = stringResource(R.string.payment_methods_label),
        notScrollableContent = { Loading() }
    )
}

@Composable
private fun Loading() {
    ShimmerAnimatedItem(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    ShimmerAnimatedItem(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    (1..5).forEach { _ ->
        ShimmerAnimatedItem(
            itemHeight = SDKTheme.dimensions.paymentMethodItemHeight,
            borderRadius = 6.dp
        )
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoadingPreview() {
    Content()
}
