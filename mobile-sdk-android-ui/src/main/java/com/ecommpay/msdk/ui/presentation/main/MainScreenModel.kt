package com.ecommpay.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.ecommpay.msdk.core.domain.entities.init.PaymentMethod
import com.ecommpay.msdk.ui.base.mvi.UiEvent
import com.ecommpay.msdk.ui.base.mvi.UiState
import com.ecommpay.msdk.ui.presentation.init.InitScreenUiEvent

@Immutable
internal sealed class MainScreenUiEvent : UiEvent {

}

@Immutable
internal data class MainScreenState(
    val data: List<PaymentMethod>,
) : UiState {

    companion object {
        fun initial() = MainScreenState(
            data = emptyList(),
        )
    }
}
