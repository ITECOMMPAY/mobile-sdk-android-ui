package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.paymentpage.ui.msdk.sample.ui.navigation.NavRouts


@Composable
internal fun AdditionalFieldsButton(navController: NavController) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp),
        onClick = {
            navController.navigate(NavRouts.AdditionalFields.route)
        }) {
        Text(text = "Additional fields", color = Color.White, fontSize = 18.sp)
    }
}