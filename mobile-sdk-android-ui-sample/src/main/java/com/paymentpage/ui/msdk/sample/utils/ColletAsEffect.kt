package com.paymentpage.ui.msdk.sample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> Flow<T>.CollectAsEffect(
    block: (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onEach(block).launchIn(this)
    }
}
