package com.paymentpage.ui.msdk.sample.ui.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

abstract class BaseViewModel<VI : ViewIntents, VS : ViewState>(viewUS: BaseViewUseCase<VI, VS>) : ViewModel(), ViewUseCaseContract<VI, VS> by viewUS {
    init {
        useCaseScope = viewModelScope
    }
}