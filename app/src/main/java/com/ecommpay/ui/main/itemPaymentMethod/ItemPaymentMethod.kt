package com.ecommpay.ui.main.itemPaymentMethod

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommpay.ui.main.MainViewIntents
import com.ecommpay.ui.theme.PaymentMethodsScreen_background


@Composable
fun ItemPaymentMethod(
    name: String,
    @DrawableRes icon: Int,
    listener: (intent: MainViewIntents) -> Unit,
) {
    val padding = 10.dp
    Row(
        modifier = Modifier
            .clickable { listener(MainViewIntents.Click(name = name)) }
            .fillMaxWidth()
            .padding(padding)
            .height(80.dp)
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(5.dp))
            .padding(padding)
    ) {
        Image(
            modifier = Modifier
            .align(Alignment.CenterVertically),
            painter = painterResource(id = icon),
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


