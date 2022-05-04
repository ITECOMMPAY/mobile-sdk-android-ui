package com.ecommpay.msdk.ui.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel<VD : ViewData> : ViewModel() {

    private val _viewState: MutableLiveData<ViewStates<VD>> = MutableLiveData(DefaultViewStates.Default(defaultViewData()))
    val viewState: LiveData<ViewStates<VD>> = _viewState

    private val _viewAction: MutableLiveData<ViewActions> = MutableLiveData(DefaultViewActions.Default)
    val viewAction: LiveData<ViewActions> = _viewAction

    open fun pushIntent(viewIntent: ViewIntents?) {
        viewIntent?.let {intent -> viewState.value?.let { currentState ->
            obtainIntent(intent, currentState)
            when (currentState) {
                is DefaultViewStates.Display<*> -> reduce(intent, currentState as DefaultViewStates.Display<VD>)
                is DefaultViewStates.Loading<*> -> reduce(intent, currentState as DefaultViewStates.Loading<VD>)
                is DefaultViewStates.DisabledNetwork<*> -> reduce(intent, currentState as DefaultViewStates.DisabledNetwork<VD>)
            }
        } }

    }

    protected open fun obtainIntent(intent: ViewIntents, currentState: ViewStates<VD>) = Unit
    protected open fun reduce(intent: ViewIntents, currentState: DefaultViewStates.Display<VD>) = Unit
    protected open fun reduce(intent: ViewIntents, currentState: DefaultViewStates.Loading<VD>) = Unit
    protected open fun reduce(intent: ViewIntents, currentState: DefaultViewStates.DisabledNetwork<VD>) = Unit

    // срабатывает при входе
    open fun entryPoint() = Unit

    // срабатывает при входе
    open fun entryPoint(arguments: Bundle?) = Unit

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