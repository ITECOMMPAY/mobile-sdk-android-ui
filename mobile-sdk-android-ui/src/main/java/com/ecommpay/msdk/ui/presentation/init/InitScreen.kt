package com.ecommpay.msdk.ui.presentation.init

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.navigation.Route
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.navigate
import com.ecommpay.msdk.ui.views.common.SDKFooter
import com.ecommpay.msdk.ui.views.common.SDKTopBar
import com.ecommpay.msdk.ui.views.shimmer.ShimmerAnimation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(viewModel: InitViewModel = viewModel(), navController: NavController) {

    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            if (it.isInitLoaded && it.data.isNotEmpty()) {
                navController.navigate(Route.Main, it.data)
            }

        }.collect()
    }
    Content()
}


@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(SDKTheme.colors.backgroundPaymentMethods),
        content = {
            Column(
                modifier = Modifier.padding(SDKTheme.dimensions.paddingDp20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SDKTopBar(
                    title = PaymentActivity.stringResourceManager.payment.methodsTitle
                        ?: "Payment Methods",
                    arrowIcon = null
                )
                Loading()
                Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp15))

                SDKFooter(
                    iconLogo = R.drawable.sdk_logo,
                    poweredByText = "Powered by"
                )
            }
        }
    )
}

@Composable
private fun Loading() {
    ShimmerAnimation(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    ShimmerAnimation(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimation(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimation(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        (1..5).forEach { _ ->
            ShimmerAnimation(
                itemHeight = SDKTheme.dimensions.paymentMethodItemHeight,
                borderRadius = 6.dp
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.paddingDp10))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoadingPreview() {
    Content()
}
