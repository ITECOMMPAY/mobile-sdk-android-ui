package com.paymentpage.msdk.ui.presentation.main.views.method

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.ui.LocalAdditionalFields
import com.paymentpage.msdk.ui.LocalMainViewModel
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.presentation.main.models.UIPaymentMethod
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.views.customerFields.CustomerFields
import com.paymentpage.msdk.ui.views.expandable.ExpandableItem


@Composable
internal fun GooglePayItem(
    isExpand: Boolean,
    method: UIPaymentMethod.UIGooglePayPaymentMethod,
    onItemSelected: ((method: UIPaymentMethod) -> Unit),
    onItemUnSelected: ((method: UIPaymentMethod) -> Unit),
) {
    val viewModel = LocalMainViewModel.current
    val customerFields = remember { method.paymentMethod?.customerFields }
    if (customerFields.isNullOrEmpty()) {
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
                .height(50.dp)
                .fillMaxWidth()
        )
    } else {
        ExpandableItem(
            index = method.index,
            name = method.title,
            iconUrl = method.paymentMethod?.iconUrl,
            headerBackgroundColor = SDKTheme.colors.backgroundColor,
            onExpand = { onItemSelected(method) },
            onCollapse = { onItemUnSelected(method) },
            isExpanded = isExpand,
            fallbackIcon = painterResource(id = SDKTheme.images.googlePayMethodResId),
        ) {
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding10))
            CustomerFields(
                customerFields = customerFields,
                additionalFields = LocalAdditionalFields.current
            )
            Spacer(modifier = Modifier.size(SDKTheme.dimensions.padding22))
            Button(
                onClick = {
                    //TODO need validate
              
                },
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.googlepay_button_logo),
                        contentDescription = null
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                ),
                shape = SDKTheme.shapes.radius6,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            )
        }
    }

}