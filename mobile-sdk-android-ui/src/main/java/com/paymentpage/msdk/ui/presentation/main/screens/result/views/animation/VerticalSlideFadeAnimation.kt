package com.paymentpage.msdk.ui.presentation.main.screens.result.views.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable

@Composable
internal fun VerticalSlideFadeAnimation(
    visibleState: MutableTransitionState<Boolean>,
    duration: Int,
    delay: Int = 0,
    initialOffsetYRatio: Float = 1.0f,
    direction: AnimationDirection = AnimationDirection.UP,
    content: @Composable (AnimatedVisibilityScope.() -> Unit)
) {
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = tween(
                delayMillis = delay,
                durationMillis = duration
            )
        ) + slideInVertically(
            animationSpec = tween(
                delayMillis = delay,
                durationMillis = duration
            ),
            initialOffsetY = { (direction.value * it * initialOffsetYRatio).toInt() }
        ),
        content = content
    )
}

internal enum class AnimationDirection(val value: Int) {
    UP(1),
    DOWN(-1)
}