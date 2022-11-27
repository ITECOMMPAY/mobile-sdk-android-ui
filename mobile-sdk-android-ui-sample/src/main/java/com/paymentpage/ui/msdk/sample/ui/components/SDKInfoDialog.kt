package com.paymentpage.ui.msdk.sample.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SDKInfoDialog(
    iconID: Int,
    title:String,
    message: String,
    buttonText:String,
    onDismiss: () -> Unit)
{

    Dialog(
        onDismissRequest = onDismiss
    ){
        Column(
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = iconID),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                text = title
            )

            Text(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp),
                text = message
            )

            Spacer(modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .height(1.dp))

            Box(
                modifier = Modifier
                    .height(44.dp)
                    .clickable {
                        onDismiss()
                    }
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                    Text(
                        text = buttonText
                    )
            }
        }
    }
}