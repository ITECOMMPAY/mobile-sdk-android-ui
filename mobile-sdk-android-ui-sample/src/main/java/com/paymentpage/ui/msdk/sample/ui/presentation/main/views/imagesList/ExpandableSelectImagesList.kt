package com.paymentpage.ui.msdk.sample.ui.presentation.main.views.imagesList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewIntents
import com.paymentpage.ui.msdk.sample.ui.presentation.main.MainViewModel

@Composable
internal fun ExpandableSelectImagesList(
    viewModel: MainViewModel = viewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()

    val rotationState by animateFloatAsState(if (viewState?.isExpandedSelectImagesList == true) 0f else 180f)
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = MaterialTheme.shapes.small
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 400,
                    easing = LinearOutSlowInEasing
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(modifier = Modifier
                .clickable(
                    indication = null, //отключаем анимацию при клике
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        viewModel.pushIntent(MainViewIntents.ExpandResourceImagesList)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Select logo",
                    textAlign = TextAlign.Start,
                )
                Image(
                    modifier = Modifier.rotate(rotationState),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    colorFilter = ColorFilter.tint(Color.Black),
                    contentDescription = null,
                )
            }
            AnimatedVisibility(visible = (viewState?.isExpandedSelectImagesList == true)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Spacer(modifier = Modifier.height(10.dp))
                        SelectImagesList()
                        Spacer(modifier = Modifier.height(10.dp))
                        viewState?.paymentData?.bitmap?.asImageBitmap()?.let {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .background(Color.LightGray),
                                    bitmap = it,
                                    contentDescription = null)
                            }
                        }
                    }
                )
            }
        }
    }
}