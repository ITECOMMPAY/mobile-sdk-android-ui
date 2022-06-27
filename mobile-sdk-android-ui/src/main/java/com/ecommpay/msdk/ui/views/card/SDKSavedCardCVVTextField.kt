@file:Suppress("unused")

package com.ecommpay.msdk.ui.views.card

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@SuppressLint("PrivateResource", "ComposableNaming")
@ExperimentalComposeUiApi
@Composable
fun SDKSavedCardCVVTextField(
    cardUrlLogo: String,
    cardNumber: String,
): TextFieldValue {
    //Запоминаем стейт поля ввода CVV
    var cvvTextValueState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    //Запоминаем стейт иконки замка
    var isLocked by rememberSaveable { mutableStateOf(true) }
    val lockIcon = painterResource(id = android.R.drawable.ic_lock_idle_lock)
    val unlockIcon = painterResource(id = android.R.drawable.ic_lock_idle_low_battery)
    //Получаем контроллер системной клавиатуры
    val keyboardController = LocalSoftwareKeyboardController.current
    //Получаем фокус-реквестер и фокус-менеджер для того, чтобы программно передать фокус CVV-полю при открытии экрана
    val requester = FocusRequester()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(
        key1 = Unit,
        block = {
            //Передаём фокус CVV-полю
            requester.requestFocus()
        })
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(5.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cardUrlLogo)
                .crossfade(true)
                .build(),
            fallback = painterResource(androidx.appcompat.R.drawable.abc_star_black_48dp),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(width = 70.dp, height = 50.dp)
                .padding(10.dp)
        )
        Text(text = cardNumber)
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = cvvTextValueState,
            //Ограничиваем количество вводимым символов до 3
            onValueChange = { if (it.text.length <= 3) cvvTextValueState = it },
            //Ограничиваем количество вводимых строк до 1
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                //Выбор типа системной клавиатуры
                keyboardType = KeyboardType.NumberPassword,
                //Задать экшн для поля, чтобы системная клавиатура понимала, что это конечное поле
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    //Скрыть клавиатуру и очистить фокус после нажатия кнопки Done на системной клавиатуре
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .size(width = 70.dp, height = 56.dp)
                //Задаём фокус-реквестер CVV-полю
                .focusRequester(requester),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.surface,
                unfocusedIndicatorColor = MaterialTheme.colors.surface
            ),
            placeholder = {
                Text(
                    text = "CVV",
                    fontSize = 14.sp
                )
            },
            //Меняем маску на символ звёдочки
            visualTransformation = if (isLocked) PasswordVisualTransformation('*') else VisualTransformation.None,
        )
        Image(
            painter = if (isLocked) lockIcon else {
                unlockIcon
            },
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    isLocked = !isLocked
                })
    }
    return cvvTextValueState
}