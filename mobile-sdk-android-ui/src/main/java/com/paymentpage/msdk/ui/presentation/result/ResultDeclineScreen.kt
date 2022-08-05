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
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.paymentDateToPatternDate
import com.paymentpage.msdk.ui.views.common.PaymentOverview
import com.paymentpage.msdk.ui.views.common.SDKFooter
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.presentation.result.views.ResultTableInfo

@Composable
internal fun ResultDeclineScreen(
    onClose: (Payment) -> Unit
) {
    val viewModel = LocalMainViewModel.current
    val payment =
        viewModel.lastState.payment ?: throw IllegalStateException("Not found payment in State")

    val valueTitleCardWallet = when (val method = viewModel.lastState.currentMethod) {
        is UIPaymentMethod.UICardPayPaymentMethod, is UIPaymentMethod.UISavedCardPayPaymentMethod -> {
             "${payment.account?.type?.uppercase() ?: ""} ${payment.account?.number}"
        }
        is UIPaymentMethod.UIApsPaymentMethod, is UIPaymentMethod.UIGooglePayPaymentMethod -> {
            method.paymentMethod.translations["title"] ?: ""
        }
        else -> {
            payment.account?.type ?: ""
        }
    }

    BackHandler(true) { onClose(payment) }

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
                    text = PaymentActivity.stringResourceManager.getStringByKey("title_result_error_payment"),
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
            PaymentOverview()
            Spacer(modifier = Modifier.size(15.dp))
            ResultTableInfo(
                titleKeyWithValueMap = mapOf(
                    "title_card_wallet" to
                            valueTitleCardWallet,
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

