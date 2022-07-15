package com.paymentpage.msdk.ui.presentation.init

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.navigation.Navigator
import com.paymentpage.msdk.ui.navigation.Route
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.viewModelFactory
import com.paymentpage.msdk.ui.views.common.Footer
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.shimmer.ShimmerAnimatedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun InitScreen(
    navigator: Navigator,
    delegate: PaymentDelegate
) {
    val paymentInfo = LocalPaymentInfo.current
    val recurrentInfo = LocalRecurrentInfo.current
    val threeDSecureInfo = LocalThreeDSecureInfo.current
    val initInteractor = LocalMsdkSession.current.getInitInteractor()
    val viewModel: InitViewModel = viewModel(
        factory = viewModelFactory {
            InitViewModel(
                initInteractor = initInteractor,
                paymentInfo = paymentInfo,
                recurrentInfo = recurrentInfo,
                threeDSecureInfo = threeDSecureInfo
            )
        }
    )

    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when {
                it.error != null -> delegate.onError(it.error.code, it.error.message)
                it.isInitLoaded -> navigator.navigateTo(Route.Main)
            }
        }.collect()
    }
    Content(delegate)
}


@Composable
private fun Content(delegate: PaymentDelegate? = null) {
    val showDialogDismissDialog = remember { mutableStateOf(false) }
    BackHandler(true) {
        showDialogDismissDialog.value = true
    }
    SDKScaffold(
        title = stringResource(R.string.payment_methods_label),
        notScrollableContent = { Loading() },
        footerContent = {
            Footer(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = { showDialogDismissDialog.value = true }
    )
    if (showDialogDismissDialog.value)
        AlertDialog(
            text = { Text(text = stringResource(R.string.payment_dismiss_confirm_message)) },
            onDismissRequest = { showDialogDismissDialog.value = false },
            confirmButton = {
                TextButton(onClick = { delegate?.onCancel() })
                { Text(text = stringResource(R.string.ok_label)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialogDismissDialog.value = false })
                { Text(text = stringResource(R.string.cancel_label)) }
            }
        )
}

@Composable
private fun Loading() {
    ShimmerAnimatedItem(
        itemHeight = 20.dp,
        itemWidth = 125.dp,
        borderRadius = 4.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
    ShimmerAnimatedItem(
        itemHeight = 150.dp,
        borderRadius = 12.dp
    )
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
    Row {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShimmerAnimatedItem(
                itemHeight = 50.dp,
                borderRadius = 6.dp,
            )
        }
    }
    Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
    (1..5).forEach { _ ->
        ShimmerAnimatedItem(
            itemHeight = 50.dp,
            borderRadius = 6.dp
        )
        Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoadingPreview() {
    Content()
}
