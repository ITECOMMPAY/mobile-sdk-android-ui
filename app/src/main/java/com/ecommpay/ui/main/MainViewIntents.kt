package com.ecommpay.ui.main

import com.ecommpay.ui.base.ViewIntents

sealed class MainViewIntents: ViewIntents {
    data class Click (val name: String): MainViewIntents()
}