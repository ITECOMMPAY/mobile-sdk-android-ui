package com.paymentpage.ui.msdk.sample.domain.ui.base


import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.navigation.NavigationViewUC
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewIntents
import com.paymentpage.ui.msdk.sample.domain.ui.sample.SampleViewUC
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewUC<VI : ViewIntents, VS : ViewState>(
    initViewState: VS
) : ViewUseCaseContract<VI, VS> {

    private val _viewState = MutableStateFlow(initViewState)
    final override val viewState = _viewState

    private var isFirstRun = true

    //Отменяется при удалении useCase
    protected val useCaseScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    //Отменяется, если нет подписчиков на viewState или отменился useCaseScope
    private var job = SupervisorJob(useCaseScope.coroutineContext.job)
    protected val visibleUseCaseScope: CoroutineScope get() = CoroutineScope(job + Dispatchers.Main.immediate)

    private val _viewAction = MutableSharedFlow<ViewActions>(extraBufferCapacity = 5)
    final override val viewAction: SharedFlow<ViewActions> = _viewAction

    private val subscriptionCount = _viewState.subscriptionCount.map { count -> count > 0 }
        .onEach { isActive ->
            when {
                isActive -> {
                    isFirstRun = false
                    visible()
                }
                !isActive && !isFirstRun -> invisible()
            }
        }
        .launchIn(useCaseScope)


    private suspend fun visible() {
        onVisible()
    }

    private suspend fun invisible() {
        job.cancel()
        job = SupervisorJob(useCaseScope.coroutineContext.job)
        onInvisible()
    }

    override fun clear() {
        useCaseScope.cancel()
        onClear()
    }

    override fun pushExternalIntent(intent: ViewIntents) {
        when (intent) {
            is NavigationViewIntents -> ViewUseCasesStore.getOrNull<NavigationViewUC>("Navigation")
                ?.pushIntent(intent)
            is SampleViewIntents -> ViewUseCasesStore.getOrNull<SampleViewUC>("Sample")
                ?.pushIntent(intent)
        }
    }

    final override fun pushIntent(intent: VI) {
        useCaseScope.launch {
            reduce(intent)
        }
    }

    protected fun updateState(newState: VS) {
        useCaseScope.launch {
            _viewState.emit(newState)
            delay(30)
            onUpdateState(newState)
        }
    }

    suspend fun launchAction(action: ViewActions) {
        _viewAction.emit(action)
        delay(30)
    }

    //Overrides
    protected open suspend fun onUpdateState(newState: VS) {}
    protected abstract suspend fun reduce(viewIntent: VI)
    protected open suspend fun onVisible() {}
    protected open suspend fun onInvisible() {}
    protected open fun onClear() {}

}