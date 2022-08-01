package com.paymentpage.ui.msdk.sample.ui.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewUseCase<VI : ViewIntents, VS : ViewState> : ViewUseCaseContract<VI, VS> {

    override lateinit var useCaseScope: CoroutineScope
    private val _viewState = MutableStateFlow<VS?>(null)

    override val viewState by lazy { _viewState.stateIn(useCaseScope, SharingStarted.Lazily, null) }
    private val _viewAction = MutableSharedFlow<ViewActions>(extraBufferCapacity = 5)
    override val viewAction: SharedFlow<ViewActions> = _viewAction

    protected abstract suspend fun reduce(viewIntent: VI)

    override fun pushIntent(intent: VI) {
        useCaseScope.launch {
            reduce(intent)
        }
    }

    protected suspend fun updateState(newState: VS?) {
        newState?.let {
            _viewState.emit(newState)
            while (newState != viewState.value) delay(15)
        }
    }

    protected suspend fun launchAction(action: ViewActions) {
        _viewAction.emit(action)
    }
}