package com.ecommpay.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.PaymentActivity
import com.ecommpay.msdk.ui.PaymentOptions
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.presentation.main.models.UIPaymentMethod
import com.ecommpay.msdk.ui.presentation.main.views.expandable.ExpandableItem
import com.ecommpay.msdk.ui.theme.SDKTheme
import com.ecommpay.msdk.ui.utils.extensions.amountToCoins
import com.ecommpay.msdk.ui.views.button.PayButton
import com.ecommpay.msdk.ui.views.customerFields.CustomerFields

@Composable
internal fun GooglePayItem(
    isExpand: Boolean,
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    paymentOptions: PaymentOptions,
    onItemSelected: ((method: UIPaymentMethod) -> Unit)? = null,
) {
    val customerFields = remember { method.paymentMethod?.customerFields }
    if (customerFields.isNullOrEmpty()) {
        Box() {
            Button(
                onClick = {},
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.googlepay_button_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                ),
                shape = SDKTheme.shapes.radius6,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
            )
        }
    } else {
        ExpandableItem(
            index = method.index,
            name = method.title,
            iconUrl = method.paymentMethod?.iconUrl,
            headerBackgroundColor = SDKTheme.colors.backgroundColor,
            onExpand = { onItemSelected?.invoke(method) },
            isExpanded = isExpand,
            fallbackIcon = painterResource(id = SDKTheme.images.googlePayMethodResId),
        ) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            CustomerFields(
                customerFields = customerFields,
                additionalFields = paymentOptions.additionalFields
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            PayButton(
                payLabel = PaymentActivity.stringResourceManager.getStringByKey("button_pay"),
                amount = paymentOptions.paymentInfo?.paymentAmount.amountToCoins(),
                currency = paymentOptions.paymentInfo?.paymentCurrency?.uppercase() ?: "",
                isEnabled = true,
                onClick = {}
            )
        }
    }

}