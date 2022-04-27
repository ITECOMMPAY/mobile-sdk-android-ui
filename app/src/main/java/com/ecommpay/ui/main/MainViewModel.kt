package com.ecommpay.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var isFirstRun: Boolean = true
        get() {
            return if (field) {
                field = false
                true
            } else field
        }
}
