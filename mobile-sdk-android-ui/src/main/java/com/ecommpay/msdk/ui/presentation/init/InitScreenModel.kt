package com.ecommpay.msdk.ui.presentation.init

import androidx.compose.runtime.Immutable
import com.ecommpay.msdk.ui.base.mvi.UiEvent
import com.ecommpay.msdk.ui.base.mvi.UiState
import com.ecommpay.msdk.ui.presentation.main.data.UIPaymentMethod

@Immutable
internal sealed class InitScreenUiEvent : UiEvent {
    object ShowLoading : InitScreenUiEvent()
    class InitLoaded(val data: List<UIPaymentMethod>) : InitScreenUiEvent()
}

@Immutable
internal data class InitScreenState(
    val isInitLoaded: Boolean,
    val data: List<UIPaymentMethod>
) : UiState {

    companion object {
        fun initial() = InitScreenState(
            isInitLoaded = false,
            data = emptyList()
        )
    }

    override fun toString(): String {
        return "isInitLoaded: $isInitLoaded, data.size: ${data.size}"
    }
}
