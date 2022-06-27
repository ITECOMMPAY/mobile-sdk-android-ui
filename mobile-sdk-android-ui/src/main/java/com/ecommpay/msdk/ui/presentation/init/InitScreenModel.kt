package com.ecommpay.msdk.ui.presentation.init

import androidx.compose.runtime.Immutable
import com.ecommpay.msdk.ui.base.mvi.UiEvent
import com.ecommpay.msdk.ui.base.mvi.UiState

@Immutable
sealed class InitScreenUiEvent : UiEvent {
    object ShowLoading : InitScreenUiEvent()
    object InitLoaded : InitScreenUiEvent()
}

@Immutable
data class InitScreenState(
    val isInitLoaded: Boolean
) : UiState {

    companion object {
        fun initial() = InitScreenState(
            isInitLoaded = false
        )
    }

    override fun toString(): String {
        return "isInitLoaded: $isInitLoaded"
    }
}
