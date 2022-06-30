package com.ecommpay.msdk.ui.presentation.init

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ecommpay.msdk.ui.PaymentDelegate
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Route
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.navigate
import com.ecommpay.msdk.ui.views.common.SDKScaffold
import com.ecommpay.msdk.ui.views.shimmer.SDKShimmerAnimatedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(
    navController: NavController,
    delegate: PaymentDelegate
) {
    val viewModel: InitViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when {
                it.error != null -> delegate.onError(it.error.code, it.error.message)
                it.isInitLoaded && it.data.isNotEmpty() ->
                    navController.navigate(
                        Route.Main,
                        it.data
                    )
            }
        }.collect()
    }
    Content()
}


@Composable
private fun Content() {
    SDKScaffold(
        title = stringResource(R.string.payment_methods_label),
        notScrollableContent = {
            Loading()
        }
    )
}

@Composable
private fun Loading() {
    SDKShimmerAnimatedItem(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    SDKShimmerAnimatedItem(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            SDKShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            SDKShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    (1..5).forEach { _ ->
        SDKShimmerAnimatedItem(
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
