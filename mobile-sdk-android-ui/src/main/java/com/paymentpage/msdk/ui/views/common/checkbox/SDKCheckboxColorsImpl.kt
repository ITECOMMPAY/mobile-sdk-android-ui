package com.paymentpage.msdk.ui.views.common.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.CheckboxColors
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import com.paymentpage.msdk.ui.theme.SDKTheme
import com.paymentpage.msdk.ui.theme.selectedCheckBox
import com.paymentpage.msdk.ui.theme.unselectedCheckBox

private const val BoxInDuration = 50
private const val BoxOutDuration = 100

//just copied from DefaultCheckboxColors
private class SDKCheckboxColorsImpl(
    private val checkedCheckmarkColor: Color,
    private val uncheckedCheckmarkColor: Color,
    private val checkedBoxColor: Color,
    private val uncheckedBoxColor: Color,
    private val disabledCheckedBoxColor: Color,
    private val disabledUncheckedBoxColor: Color,
    private val disabledIndeterminateBoxColor: Color,
    private val checkedBorderColor: Color,
    private val uncheckedBorderColor: Color,
    private val disabledBorderColor: Color,
    private val disabledIndeterminateBorderColor: Color,
) : CheckboxColors {
    @Composable
    override fun checkmarkColor(state: ToggleableState): State<Color> {
        val target = if (state == ToggleableState.Off) {
            uncheckedCheckmarkColor
        } else {
            checkedCheckmarkColor
        }

        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        return animateColorAsState(target, tween(durationMillis = duration))
    }

    @Composable
    override fun boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val target = if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBoxColor
                ToggleableState.Off -> uncheckedBoxColor
            }
        } else {
            when (state) {
                ToggleableState.On -> disabledCheckedBoxColor
                ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
                ToggleableState.Off -> disabledUncheckedBoxColor
            }
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
            animateColorAsState(target, tween(durationMillis = duration))
        } else {
            rememberUpdatedState(target)
        }
    }

    @Composable
    override fun borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val target = if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBorderColor
                ToggleableState.Off -> uncheckedBorderColor
            }
        } else {
            when (state) {
                ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
                ToggleableState.On, ToggleableState.Off -> disabledBorderColor
            }
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
            animateColorAsState(target, tween(durationMillis = duration))
        } else {
            rememberUpdatedState(target)
        }
    }
}

object SDKCheckboxDefaults {
    @Composable
    fun colors(
        checkedColor: Color = selectedCheckBox,
        uncheckedColor: Color = unselectedCheckBox,
        checkmarkColor: Color =
            if (!SDKTheme.colors.isDarkTheme)
                SDKTheme.colors.background
            else
                SDKTheme.colors.neutral,
        uncheckedBoxColor: Color = unselectedCheckBox,
        disabledIndeterminateColor: Color = checkedColor.copy(alpha = ContentAlpha.disabled),
    ): CheckboxColors {
        return remember(
            checkedColor,
            uncheckedColor,
            checkmarkColor,
            uncheckedBoxColor,
            disabledIndeterminateColor,
        ) {
            SDKCheckboxColorsImpl(
                checkedBorderColor = checkedColor,
                checkedBoxColor = checkedColor,
                checkedCheckmarkColor = checkmarkColor,

                uncheckedCheckmarkColor = checkmarkColor.copy(alpha = 0f),
                uncheckedBoxColor = uncheckedBoxColor,
                uncheckedBorderColor = uncheckedColor,

                disabledCheckedBoxColor = checkedColor,
                disabledUncheckedBoxColor = uncheckedBoxColor,
                disabledBorderColor = uncheckedColor,

                disabledIndeterminateBoxColor = disabledIndeterminateColor,
                disabledIndeterminateBorderColor = disabledIndeterminateColor,
            )
        }
    }
}