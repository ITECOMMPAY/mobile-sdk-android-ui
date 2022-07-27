package com.paymentpage.msdk.ui.presentation.result

import androidx.activity.compose.BackHandler
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
import com.paymentpage.msdk.ui.PaymentActivity
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.paymentDateToPatternDate
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.presentation.result.views.ResultTableInfo

@Composable
internal fun ResultSuccessScreen(
    onClose: (Payment) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.lastState.payment ?: throw IllegalStateException("Not found payment in State")

    BackHandler(true) { onClose(payment) }

    SDKScaffold(
        notScrollableContent = {},
        scrollableContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = SDKTheme.images.successLogo),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(15.dp))
                Text(
                    text = PaymentActivity.stringResourceManager.getStringByKey("title_result_succes_payment"),
                    style = SDKTheme.typography.s24Bold,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            ResultTableInfo(
                titleKeyWithValueMap = mapOf(
                    "title_card_wallet" to
                            "${payment.account?.type?.uppercase() ?: ""} ${payment.account?.number ?: ""}",
                    "title_payment_id" to
                            "${payment.id}",
                    "title_payment_date" to
                            payment.date?.paymentDateToPatternDate("dd.MM.yyyy HH:mm"),
                    "complete_field_payment_vat_operation_rate" to
                            payment.completeFields?.find { it.name == "complete_field_payment_vat_operation_rate" }?.value,
                    "complete_field_payment_vat_operation_amount" to
                            payment.completeFields?.find { it.name == "complete_field_payment_vat_operation_amount" }?.value,
                    "complete_field_payment_vat_operation_currency" to
                            payment.completeFields?.find { it.name == "complete_field_payment_vat_operation_currency" }?.value,
                )
            )
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

