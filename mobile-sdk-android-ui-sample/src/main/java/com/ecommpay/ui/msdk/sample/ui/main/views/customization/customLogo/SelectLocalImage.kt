package com.ecommpay.ui.msdk.sample.ui.main.views.customization.customLogo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectLocalImage(
    uriListener: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            uriListener(uri)
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                launcher.launch("image/*")
            }
        ) {
            Text(text = "Select local logo", color = Color.White, fontSize = 18.sp)
        }
    }
}