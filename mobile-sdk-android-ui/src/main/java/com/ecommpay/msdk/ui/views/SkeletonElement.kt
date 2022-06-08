package com.ecommpay.msdk.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SkeletonElement(
    modifier: Modifier
) {
    Box(
        modifier = modifier.background(Color.Gray)
    )
}