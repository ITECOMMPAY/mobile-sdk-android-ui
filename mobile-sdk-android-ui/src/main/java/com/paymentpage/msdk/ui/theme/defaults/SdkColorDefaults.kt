package com.paymentpage.msdk.ui.theme.defaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.paymentpage.msdk.ui.theme.LocalColors
import com.paymentpage.msdk.ui.theme.SDKColorButton
import com.paymentpage.msdk.ui.theme.SDKColorInput
import com.paymentpage.msdk.ui.theme.darkButtonArrow
import com.paymentpage.msdk.ui.theme.darkButtonCard
import com.paymentpage.msdk.ui.theme.darkButtonText
import com.paymentpage.msdk.ui.theme.darkInputDisabled
import com.paymentpage.msdk.ui.theme.darkInputErrorBackground
import com.paymentpage.msdk.ui.theme.darkInputErrorBorder
import com.paymentpage.msdk.ui.theme.darkInputNeutral
import com.paymentpage.msdk.ui.theme.darkInputTextAdditional
import com.paymentpage.msdk.ui.theme.darkInputTextPrimary
import com.paymentpage.msdk.ui.theme.lightButtonArrow
import com.paymentpage.msdk.ui.theme.lightButtonCard
import com.paymentpage.msdk.ui.theme.lightButtonText
import com.paymentpage.msdk.ui.theme.lightInputDisabled
import com.paymentpage.msdk.ui.theme.lightInputErrorBackground
import com.paymentpage.msdk.ui.theme.lightInputErrorBorder
import com.paymentpage.msdk.ui.theme.lightInputNeutral
import com.paymentpage.msdk.ui.theme.lightInputTextAdditional
import com.paymentpage.msdk.ui.theme.lightInputTextPrimary
import com.paymentpage.msdk.ui.theme.selectColor

object SdkColorDefaults {

    @Composable
    internal fun buttonColor(
        text: Color = selectColor(lightButtonText, darkButtonText),
        arrow: Color = selectColor(lightButtonArrow, darkButtonArrow),
        background: Color = selectColor(LocalColors.current.secondary, LocalColors.current.primary),
        circleBackground: Color = selectColor(
            LocalColors.current.primary,
            LocalColors.current.secondary
        ),
        card: Color = selectColor(lightButtonCard, darkButtonCard),
    ): SDKColorButton = object : SDKColorButton {
        @Composable
        override fun text(): State<Color> {
            return rememberUpdatedState(text)
        }

        @Composable
        override fun arrow(): State<Color> {
            return rememberUpdatedState(arrow)
        }

        @Composable
        override fun background(): State<Color> {
            return rememberUpdatedState(background)
        }

        @Composable
        override fun circleBackground(): State<Color> {
            return rememberUpdatedState(circleBackground)
        }

        @Composable
        override fun card(): State<Color> {
            return rememberUpdatedState(card)
        }
    }

    @Composable
    internal fun inputColor(
        defaultBackground: Color = selectColor(lightInputNeutral, darkInputNeutral),
        errorBackground: Color = selectColor(lightInputErrorBackground, darkInputErrorBackground),
        focusedBackground: Color = selectColor(
            LocalColors.current.secondary.copy(alpha = 0.3f),
            LocalColors.current.primary.copy(alpha = 0.3f)
        ),
        disabledBackground: Color = selectColor(lightInputDisabled, darkInputDisabled),
        focusedBorder: Color = selectColor(
            LocalColors.current.primary,
            LocalColors.current.secondary
        ),
        errorBorder: Color = selectColor(lightInputErrorBorder, darkInputErrorBorder),
        textPrimary: Color = selectColor(lightInputTextPrimary, darkInputTextPrimary),
        textAdditional: Color = selectColor(lightInputTextAdditional, darkInputTextAdditional),
    ): SDKColorInput = object : SDKColorInput {
        @Composable
        override fun defaultBackground(): State<Color> {
            return rememberUpdatedState(defaultBackground)
        }

        @Composable
        override fun errorBackground(): State<Color> {
            return rememberUpdatedState(errorBackground)
        }

        @Composable
        override fun focusedBackground(): State<Color> {
            return rememberUpdatedState(focusedBackground)
        }

        @Composable
        override fun errorBorder(): State<Color> {
            return rememberUpdatedState(errorBorder)
        }

        @Composable
        override fun disabledBackground(): State<Color> {
            return rememberUpdatedState(disabledBackground)
        }

        @Composable
        override fun focusedBorder(): State<Color> {
            return rememberUpdatedState(focusedBorder)
        }

        @Composable
        override fun textPrimary(): State<Color> {
            return rememberUpdatedState(textPrimary)
        }

        @Composable
        override fun textAdditional(): State<Color> {
            return rememberUpdatedState(textAdditional)
        }
    }


}