package com.ecommpay.msdk.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel() {
    private val _navigationState: MutableLiveData<NavigationViewActions> = MutableLiveData(NavigationViewActions.PaymentMethodsListScreen())
    val navigationState: LiveData<NavigationViewActions> = _navigationState

    fun setCurrentNavigationState(navigationViewActions: NavigationViewActions) {
        _navigationState.value = navigationViewActions
    }
}