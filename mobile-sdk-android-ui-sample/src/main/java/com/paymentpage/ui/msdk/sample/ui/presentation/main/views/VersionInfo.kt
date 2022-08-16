package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.sample.BuildConfig

@Composable
internal fun VersionInfo() {
    Row {
        Text(text = "Version:")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = BuildConfig.VERSION_NAME, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Build number:")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = BuildConfig.VERSION_CODE.toString(), fontWeight = FontWeight.Bold)
    }
}