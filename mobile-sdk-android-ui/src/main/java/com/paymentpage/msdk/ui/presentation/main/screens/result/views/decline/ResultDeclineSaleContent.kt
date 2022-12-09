package com.paymentpage.msdk.ui.presentation.main.screens.result.views.decline

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.payment.Payment
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.OverridesKeys
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.base.ErrorResult
import com.paymentpage.msdk.ui.presentation.main.screens.result.views.ResultTableInfo
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.core.getStringOverride
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold

@Composable
internal fun ResultDeclineSaleContent(
    onClose: (Payment) -> Unit,
    onError: (ErrorResult, Boolean) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.lastState.payment ?: throw IllegalStateException("Not found payment in State")

    SDKScaffold(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        notScrollableContent = {},
        scrollableContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = SDKTheme.images.errorLogo),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    text = getStringOverride(OverridesKeys.TITLE_RESULT_ERROR_PAYMENT),
                    style = SDKTheme.typography.s24Bold,
                    textAlign = TextAlign.Center
                )
                if (!payment.paymentMassage.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(
                        text = payment.paymentMassage!!,
                        style = SDKTheme.typography.s14Normal.copy(color = SDKTheme.colors.errorTextColor),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
            PaymentOverview(
                alpha = 0.4f
            )
            Spacer(modifier = Modifier.size(15.dp))
            ResultTableInfo(onError)
            Spacer(modifier = Modifier.size(15.dp))
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