package com.ecommpay.msdk.ui.entry

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.*
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethod
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodIntents
import com.ecommpay.msdk.ui.entry.itemPaymentMethod.ItemPaymentMethodViewData
import com.ecommpay.msdk.ui.views.SkeletonElement
import com.ecommpay.msdk.ui.views.SDKTopAppBar

@Composable
fun EntryScreen(
    state: ViewStates<EntryViewData>,
    intentListener: (intent: ViewIntents) -> Unit,
) {
    val shimmerColors = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.2f),
        Color.Transparent
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
    Scaffold(
        topBar = {
            SDKTopAppBar(
                title = state.viewData.topAppBarTitle,
                arrowIcon = null
            )
        },
        content = {
            Column {
                when (state) {
                    is DefaultViewStates.Loading -> {
                        SkeletonElement(
                            modifier = Modifier
                                .size(
                                    height = 20.dp,
                                    width = 125.dp
                                ))
                    }
                    else -> {
                        Text(
                            text = state.viewData.paymentDetailsTitle)
                    }
                }
                Spacer(
                    modifier = Modifier.size(10.dp)
                )
                PaymentMethodList(
                    paymentMethodList = state.viewData.paymentMethodList,
                    intentListener = intentListener,
                    modifier = Modifier.padding(it)
                )
            }
            Box(modifier = Modifier
                .background(brush)
                .fillMaxSize())
        }
    )
}

@Composable
private fun PaymentMethodList(
    paymentMethodList: List<ViewData>,
    intentListener: (intent: ItemPaymentMethodIntents) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
        paymentMethodList.forEach { itemPaymentMethodViewData ->
            when (itemPaymentMethodViewData) {
                is ShimmerViewData -> {
                    SkeletonElement(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
                is ItemPaymentMethodViewData -> {
                    ItemPaymentMethod(
                        name = itemPaymentMethodViewData.name,
                        intentListener = intentListener,
                        iconUrl = itemPaymentMethodViewData.iconUrl)
                }
            }
            Spacer(
                modifier = Modifier.size(10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenDisplayPreview() {
    EntryScreen(DefaultViewStates.Display(EntryViewData.defaultViewData)) {}
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EntryScreenLoadingPreview() {
    EntryScreen(DefaultViewStates.Loading(EntryViewData.defaultViewData)) {}
}