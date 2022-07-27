package com.paymentpage.msdk.ui.presentation.init

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
import com.paymentpage.msdk.ui.LocalInitViewModel
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentDelegate
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.presentation.main.restorePayment
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.shimmer.ShimmerAnimatedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(
    navigator: Navigator,
    delegate: PaymentDelegate
) {
    val initViewModel = LocalInitViewModel.current
    val mainViewModel = LocalMainViewModel.current
    LaunchedEffect(Unit) {
        initViewModel.state.onEach {
            when {
                it.error != null -> delegate.onError(it.error.code, it.error.message)
                it.isInitLoaded -> navigator.navigateTo(Route.Main)
                it.payment != null -> {
                    mainViewModel.restorePayment()
                }
            }
        }.collect()
    }
    Content()
}


@Composable
private fun Content() {
    SDKScaffold(
        title = stringResource(R.string.payment_methods_label),
        notScrollableContent = { Loading() },
        footerContent = { }
    )
}

@Composable
private fun Loading() {
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
    (1..5).forEach { _ ->
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
    Content()
}
