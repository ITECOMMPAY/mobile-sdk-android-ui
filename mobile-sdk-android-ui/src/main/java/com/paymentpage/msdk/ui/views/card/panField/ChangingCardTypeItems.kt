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
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethodCardType
import com.paymentpage.msdk.ui.R
import com.paymentpage.msdk.ui.utils.extensions.drawableResourceIdFromDrawableName
import kotlin.math.absoluteValue

@Composable
internal fun ChangingCardTypeItems(
    cardTypes: List<PaymentMethodCardType>,
    startIndex: Int = 0,
    onCurrentIndexChanged: ((Int) -> Unit)? = null,
) {
    val context = LocalContext.current
    var currentIndex by remember { mutableStateOf(startIndex) }
    val initialValue by remember { mutableStateOf(1.0f) }
    val targetValue by remember { mutableStateOf(0.0f) }
    val drawableNameFirstCardType = "card_type_${cardTypes[0].value}"
    val drawableNameSecondCardType =
        if (cardTypes.size > 1) "card_type_${cardTypes[1].value}" else ""
    val drawableNameThirdCardType =
        if (cardTypes.size > 2) "card_type_${cardTypes[2].value}" else ""
    val drawableIdFirstCardType = remember(drawableNameFirstCardType) {
        context.drawableResourceIdFromDrawableName(drawableNameFirstCardType)
    }
    val drawableIdSecondCardType = remember(drawableNameSecondCardType) {
        context.drawableResourceIdFromDrawableName(drawableNameSecondCardType)
    }
    val drawableIdThirdCardType = remember(drawableNameThirdCardType) {
        context.drawableResourceIdFromDrawableName(drawableNameThirdCardType)
    }
    val drawableIdsList = cardTypes.map {
        context.drawableResourceIdFromDrawableName("card_type_${it.value}")
    }
        .filter { id -> id > 0 }
        .filter { id -> id != drawableIdFirstCardType }
        .filter { id -> id != drawableIdSecondCardType }

    Row(horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp),
            painter = painterResource(id = if (drawableIdFirstCardType > 0) drawableIdFirstCardType else R.drawable.card_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        if (cardTypes.size > 1)
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp),
                painter = painterResource(id = if (drawableIdSecondCardType > 0) drawableIdSecondCardType else R.drawable.card_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        if (drawableIdsList.size > 1) {
            val drawableIdCurrentCardType = drawableIdsList[currentIndex]
            val infiniteTransition = rememberInfiniteTransition()
            val infinitelyAnimatedFloat by infiniteTransition.animateFloat(
                initialValue = initialValue,
                targetValue = targetValue,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 2000,
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
            if (currentIndex < drawableIdsList.size && infinitelyAnimatedFloat.absoluteValue == 1f) {
                if (onCurrentIndexChanged != null) {
                    onCurrentIndexChanged(currentIndex)
                }
                currentIndex++
            }
            if (currentIndex == drawableIdsList.size) currentIndex = 0
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp)
                    .alpha(1 - infinitelyAnimatedFloat),
                painter = painterResource(id = if (drawableIdCurrentCardType > 0) drawableIdCurrentCardType else R.drawable.card_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        } else if (drawableIdThirdCardType > 0) {
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .size(25.dp),
                painter = painterResource(id = drawableIdThirdCardType),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}