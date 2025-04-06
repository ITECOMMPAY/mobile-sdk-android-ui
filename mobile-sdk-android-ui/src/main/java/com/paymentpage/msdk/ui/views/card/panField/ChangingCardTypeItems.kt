package com.paymentpage.msdk.ui.views.card.panField

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodType
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.utils.extensions.paymentMethodLogoId

@Composable
internal fun ChangingCardTypeItems(
    cardTypes: List<String>,
    startIndex: Int = 0,
    onCurrentIndexChanged: ((Int) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val isDarkTheme = SDKTheme.colors.isDarkTheme
    var currentIndex by remember { mutableStateOf(startIndex) }
    var isRunning by remember { mutableStateOf(true) }

    val drawableIdStaticCardType = remember {
        context.paymentMethodLogoId(
            paymentMethodType = PaymentMethodType.CARD,
            paymentMethodName = if (cardTypes.isNotEmpty()) cardTypes[0] else "",
            isDarkTheme = isDarkTheme
        )
    }
    val drawableIdsList = cardTypes.map {
        context.paymentMethodLogoId(
            paymentMethodType = PaymentMethodType.CARD,
            paymentMethodName = it,
            isDarkTheme = isDarkTheme
        )
    }
        .filter { id -> id > 0 } //take all

    val alpha: Float by animateFloatAsState(
        targetValue = if (isRunning) 0f else 1f,
        animationSpec = tween(1500),
        finishedListener = {
            isRunning = !isRunning
            if (it == 1f) currentIndex++
            if (onCurrentIndexChanged != null) {
                onCurrentIndexChanged(currentIndex)
            }
        }
    )

    Row(horizontalArrangement = Arrangement.Center) {
        if (drawableIdsList.size > 1) {
            if (currentIndex >= drawableIdsList.size) currentIndex = 0
            val drawableIdCurrentCardType = drawableIdsList[currentIndex]
            LaunchedEffect(key1 = Unit) {
                isRunning = !isRunning
            }
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp)
                    .alpha(1 - alpha)
                    .clickable { onClick?.invoke() },
                painter = painterResource(id = if (drawableIdCurrentCardType > 0) drawableIdCurrentCardType else SDKTheme.images.defaultCardLogo),
                contentDescription = null,
                contentScale = ContentScale.Fit
            ) //image with animation
        } else if (drawableIdStaticCardType > 0) {
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp)
                    .clickable { onClick?.invoke() },
                painter = painterResource(id = drawableIdStaticCardType),
                contentDescription = null,
                contentScale = ContentScale.Fit
            ) //static image
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}