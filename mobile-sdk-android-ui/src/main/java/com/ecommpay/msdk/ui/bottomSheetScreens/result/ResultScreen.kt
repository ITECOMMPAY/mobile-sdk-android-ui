package com.ecommpay.msdk.ui.bottomSheetScreens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewStates

@Composable
fun ResultScreen(
    state: ViewStates<ResultViewData>,
    intentListener: (intent: ResultViewIntents) -> Unit,
) {
    when (state) {
        is DefaultViewStates.Content -> {
            Box(contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = ContentAlpha.disabled))
                    .fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .wrapContentSize()
                    .verticalScroll(rememberScrollState())) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.viewData.amount)
                        Text(text = state.viewData.currency)
                        Text(text = state.viewData.resultTitle)
                        Text(text = state.viewData.cardWalletTitle)
                        Text(text = state.viewData.cardWalletValue)
                        Text(text = state.viewData.paymentDateTitle)
                        Text(text = state.viewData.paymentDateValue)
                        Text(text = state.viewData.paymentIdTitle)
                        Text(text = state.viewData.paymentIdValue)
                        Text(text = state.viewData.vatAmountTitle)
                        Text(text = state.viewData.vatAmountValue)
                        Text(text = state.viewData.vatCurrencyTitle)
                        Text(text = state.viewData.vatCurrencyValue)
                        Text(text = state.viewData.vatRateTitle)
                        Text(text = state.viewData.vatRateValue)
                        Button(onClick = {
                            intentListener(ResultViewIntents.ClickDone)
                        }) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ResultScreenPreview() {
    ResultScreen(state = DefaultViewStates.Content(ResultViewData.defaultViewData),
        intentListener = {})
}