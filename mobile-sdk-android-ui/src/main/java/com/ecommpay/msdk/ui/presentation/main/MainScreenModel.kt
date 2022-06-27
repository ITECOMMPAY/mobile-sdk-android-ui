package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.ui.base.mvi.UiEvent
import com.ecommpay.msdk.ui.base.mvi.UiState

@Immutable
sealed class MainScreenUiEvent : UiEvent {
    data class ShowData(val data: List<PaymentMethod>) : MainScreenUiEvent()
    object ShowLoading : MainScreenUiEvent()
}

@Immutable
data class MainScreenState(
    val isLoading: Boolean,
    val data: List<PaymentMethod>,
) : UiState {

    companion object {
        fun initial() = MainScreenState(
            isLoading = true,
            data = emptyList(),
        )
    }

    override fun toString(): String {
        return "isLoading: $isLoading, data.size: ${data.size}"
    }
}
