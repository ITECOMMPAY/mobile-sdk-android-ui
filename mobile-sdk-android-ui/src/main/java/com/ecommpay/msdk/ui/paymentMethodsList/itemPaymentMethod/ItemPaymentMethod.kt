package com.ecommpay.msdk.ui.paymentMethodsList.itemPaymentMethod

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.msdk.ui.R
import com.ecommpay.msdk.ui.paymentMethodsList.PaymentMethodsListViewIntents


@Composable
fun ItemPaymentMethod(
    name: String,
    intentListener: (intent: PaymentMethodsListViewIntents) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { intentListener(PaymentMethodsListViewIntents.Click(name = name)) }
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
    ) {
        Image(
            modifier = Modifier
            .align(Alignment.CenterVertically),
            painter = painterResource(id = androidx.appcompat.R.drawable.abc_star_black_48dp),
            contentDescription = null)
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = name,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
            ),
        )
    }
}


