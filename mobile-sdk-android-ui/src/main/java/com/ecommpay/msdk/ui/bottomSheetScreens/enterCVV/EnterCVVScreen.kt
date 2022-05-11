package com.ecommpay.msdk.ui.bottomSheetScreens.enterCVV

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecommpay.msdk.ui.base.DefaultViewStates
import com.ecommpay.msdk.ui.base.ViewStates
import com.ecommpay.msdk.ui.views.SDKButton
import com.ecommpay.msdk.ui.views.SDKSavedCardCVVTextField
import com.ecommpay.msdk.ui.views.SDKToolBar

@Composable
fun EnterCVVScreen(
    state: ViewStates<EnterCVVViewData>,
    intentListener: (intent: EnterCVVViewIntents) -> Unit,
) {

    Box(contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .background(Color.Black.copy(alpha = ContentAlpha.disabled))
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .wrapContentSize()
            .verticalScroll(rememberScrollState())) {
            var cvvTextValueState: TextFieldValue? = null
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(bottom = 86.dp)
            ) {
                SDKToolBar(listener = {
                    intentListener(EnterCVVViewIntents.MoveToPaymentMethodsList)
                })
                cvvTextValueState = SDKSavedCardCVVTextField(
                    cardUrlLogo = state.viewData.cardUrlLogo,
                    cardNumber = state.viewData.cardNumber)
            }
            Box(
                modifier = Modifier
                    .background(
                        if (state is DefaultViewStates.Loading)
                            Color.Black.copy(alpha = ContentAlpha.disabled)
                        else Color.Transparent
                    )
                    .matchParentSize()
            )
            SDKButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = state.viewData.buttonPayText,
                //Логика активирования кнопки, если введено 3 символа CVV-кода
                isEnabled = cvvTextValueState?.text?.length == 3,
                isLoading = state is DefaultViewStates.Loading) {
                intentListener(state.viewData.payClickIntent.copy(cvv = cvvTextValueState?.text
                    ?: ""))
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun EnterCVVScreenPreview(
) {
    EnterCVVScreen(state = DefaultViewStates.Display(EnterCVVViewData.defaultViewData),
        intentListener = {})
}