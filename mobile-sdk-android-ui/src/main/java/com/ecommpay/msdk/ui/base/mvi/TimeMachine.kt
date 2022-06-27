package com.ecommpay.msdk.ui.base.mvi

interface TimeMachine<S : UiState> {
    fun addState(state: S)
    fun selectState(position: Int)
    fun getStates(): List<S>
}

class TimeTravelMachine<S : UiState>(
    private val onStateSelected: (S) -> Unit
) : TimeMachine<S> {

    private val states = mutableListOf<S>()

    override fun addState(state: S) {
        states.add(state)
    }

    override fun selectState(position: Int) {
        onStateSelected(states[position])
    }

    override fun getStates(): List<S> {
        return states
    }
}