package com.paymentpage.msdk.ui.presentation.customerFields

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.paymentpage.msdk.ui.*
import com.paymentpage.msdk.ui.presentation.main.views.detail.PaymentDetailsView
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.amountToCoins
import com.paymentpage.msdk.ui.utils.extensions.core.annotatedString
import com.paymentpage.msdk.ui.views.common.CardView
import com.paymentpage.msdk.ui.views.common.Footer
import com.paymentpage.msdk.ui.views.common.SDKScaffold
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
internal fun CustomerFieldsScreen(

) {
    val viewModel = LocalMainViewModel.current
    val customerFields = viewModel.state.value.customerFields
    val method = viewModel.state.value.method

    SDKScaffold(
        title = PaymentActivity.stringResourceManager.getStringByKey("title_payment_methods"),
        notScrollableContent = {
            PaymentDetailsView(paymentInfo = LocalPaymentInfo.current)
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
        },
        scrollableContent = {
            CardView(
                logoImage = PaymentActivity.logoImage,
                amount = LocalPaymentInfo.current.paymentAmount.amountToCoins(),
                currency = LocalPaymentInfo.current.paymentCurrency.uppercase(),
                vatIncludedTitle = when (method?.paymentMethod?.isVatInfo) {
                    true -> PaymentActivity.stringResourceManager.getStringByKey("vat_included")
                    else -> null
                }
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding15))
            CustomerFields(
                customerFields = customerFields,
                additionalFields = LocalAdditionalFields.current
            )


        },
        footerContent = {
            Footer(
                iconLogo = SDKTheme.images.sdkLogoResId,
                poweredByText = stringResource(R.string.powered_by_label),
                privacyPolicy = PaymentActivity
                    .stringResourceManager
                    .getLinkMessageByKey("footer_privacy_policy")
                    .annotatedString()
            )
        },
        onClose = { }
    )
}
