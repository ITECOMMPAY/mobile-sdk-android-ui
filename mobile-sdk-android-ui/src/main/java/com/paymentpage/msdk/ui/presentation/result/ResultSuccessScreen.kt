package com.paymentpage.msdk.ui.presentation.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun ResultSuccessScreen(
    onClose: (Payment) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val method = viewModel.lastState.currentMethod
    val payment =
        viewModel.lastState.payment ?: throw IllegalStateException("Not found payment in State")

    BackHandler(true) { onClose(payment) }

    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_result_succes_payment"),
        notScrollableContent = {
            PaymentDetailsView()
            Spacer(modifier = Modifier.size(15.dp))
        },
        scrollableContent = {
            CardView()

        },
        footerContent = {
            SDKFooter(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
            )
        },
        onClose = { onClose(payment) }
    )
}

