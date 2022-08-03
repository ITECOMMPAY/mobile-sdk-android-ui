package com.paymentpage.msdk.ui.views.card.panField

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCard
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCardType
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName

@Composable
internal fun ChangingCardTypeItems(
    firstCardType: PaymentMethodCard?,
    secondCardType: PaymentMethodCard?,
    startIndex: Int = 0,
    onCurrentIndexChanged: ((Int) -> Unit)? = null,
) {
    val context = LocalContext.current
    var currentIndex by remember { mutableStateOf(startIndex) }

    val drawableNameFirstCardType = "card_type_${firstCardType?.type?.value ?: ""}"
    val drawableNameSecondCardType = "card_type_${secondCardType?.type?.value ?: ""}"
    val drawableIdFirstCardType = remember(drawableNameFirstCardType) {
        context.drawableResourceIdFromDrawableName(drawableNameFirstCardType)
    }
    val drawableIdSecondCardType = remember(drawableNameSecondCardType) {
        context.drawableResourceIdFromDrawableName(drawableNameSecondCardType)
    }
    val drawableIdsList = PaymentMethodCardType.values().map {
        context.drawableResourceIdFromDrawableName("card_type_${it.value}")
    }.filter { id -> id > 0 }.filter { id -> id != drawableIdFirstCardType }.filter { id -> id != drawableIdSecondCardType }

    val drawableIdCurrentCardType = drawableIdsList[currentIndex]
    val infiniteTransition = rememberInfiniteTransition()
    val infinitelyAnimatedFloat by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
            ),
            repeatMode = RepeatMode.Reverse
        )
    ).apply {
        if (currentIndex < drawableIdsList.size && value == 1.0f) {
            if (onCurrentIndexChanged != null) {
                onCurrentIndexChanged(currentIndex)
            }
            currentIndex++
        }
    }
    if (currentIndex == drawableIdsList.size) currentIndex = 0

    Row(horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp),
            painter = painterResource(id = if (drawableIdFirstCardType > 0) drawableIdFirstCardType else R.drawable.card_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp),
            painter = painterResource(id = if (drawableIdSecondCardType > 0) drawableIdSecondCardType else R.drawable.card_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Image(
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp)
                .alpha(1 - infinitelyAnimatedFloat),
            painter = painterResource(id = if (drawableIdCurrentCardType > 0) drawableIdCurrentCardType else R.drawable.card_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}