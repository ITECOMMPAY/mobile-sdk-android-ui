package com.ecommpay.msdk.ui.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VD : ViewData> : ViewModel() {

    private val _viewState: MutableLiveData<ViewStates<VD>> = MutableLiveData(DefaultViewStates.Loading(defaultViewData()))
    val viewState: LiveData<ViewStates<VD>> = _viewState

    private val _viewAction: MutableLiveData<ViewActions> = MutableLiveData(DefaultViewActions.Default)
    val viewAction: LiveData<ViewActions> = _viewAction

    open fun pushIntent(viewIntent: ViewIntents?) {
        viewIntent?.let {intent -> viewState.value?.let { currentState ->
            obtainIntent(intent, currentState)
        } }

    }

    protected open fun obtainIntent(intent: ViewIntents, currentState: ViewStates<VD>) = Unit

    // срабатывает при входе
    open fun entryPoint() = Unit

    // срабатывает при входе
    open fun entryPoint(arguments: Bundle?) = Unit

    abstract fun defaultViewData(): VD

    protected fun launchAction(action: ViewActions, timeBlockUI: Long? = null) {
        _viewAction.value = action
    }

    protected fun updateState(state: ViewStates<VD>) {
        _viewState.value = state
    }

}