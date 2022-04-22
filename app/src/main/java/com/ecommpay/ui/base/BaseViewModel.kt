package com.ecommpay.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel<VD : ViewData, VI: ViewIntents> : ViewModel() {

    private val _viewState: MutableLiveData<ViewStates<VD>> = MutableLiveData(DefaultViewStates.Default(defaultViewData()))
    val viewState: LiveData<ViewStates<VD>> = _viewState

    private val _viewAction: MutableLiveData<ViewActions> = MutableLiveData(DefaultViewActions.Default)
    val viewAction: LiveData<ViewActions> = _viewAction

    open fun pushIntent(intent: VI) {
        viewState.value?.let {
            obtainIntent(intent, it)
        }
        when (val currentState = viewState.value) {
            is DefaultViewStates.Display<*> -> reduce(intent, currentState as DefaultViewStates.Display<VD>)
            is DefaultViewStates.Loading<*> -> reduce(intent, currentState as DefaultViewStates.Loading<VD>)
            is DefaultViewStates.DisabledNetwork<*> -> reduce(intent, currentState as DefaultViewStates.DisabledNetwork<VD>)
        }
    }

    protected open fun obtainIntent(intent: VI, currentState: ViewStates<VD>) {}
    protected open fun reduce(intent: VI, currentState: DefaultViewStates.Display<VD>) {}
    protected open fun reduce(intent: VI, currentState: DefaultViewStates.Loading<VD>) {}
    protected open fun reduce(intent: VI, currentState: DefaultViewStates.DisabledNetwork<VD>) {}

    open fun entryPoint() {

    } // срабатывает при входе
    abstract fun defaultViewData(): VD

    protected fun launchAction(action: ViewActions, timeBlockUI: Long? = null) {

        timeBlockUI?.let {
            viewModelScope.launch {
                val rememberState = viewState.value ?: DefaultViewStates.Default(defaultViewData())
                updateState(DefaultViewStates.BlockUI(rememberState.viewData))
                delay(it)
                updateState(rememberState)
            }
        }
        _viewAction.value = action

    }

    protected fun updateState(state: ViewStates<VD>) {
        _viewState.value = state
    }

}