package com.paymentpage.msdk.ui.presentation.main

import androidx.compose.runtime.Immutable
import com.paymentpage.msdk.core.domain.entities.init.PaymentMethod
import com.paymentpage.msdk.ui.base.mvi.UiEvent
import com.paymentpage.msdk.ui.base.mvi.UiState

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
