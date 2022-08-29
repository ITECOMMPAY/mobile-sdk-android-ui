package com.paymentpage.ui.msdk.sample.ui.presentation.main.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paymentpage.ui.msdk.sample.BuildConfig

@Composable
internal fun BrandTitle() {
    Row {
        Text(text = "Brand:")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = BuildConfig.BRAND, fontWeight = FontWeight.Bold)
    }
}