package com.bahadir.wordle.delegation.viewmodel

import androidx.lifecycle.ViewModel
import com.bahadir.wordle.presentation.base.Effect
import com.bahadir.wordle.presentation.base.Event
import com.bahadir.wordle.presentation.base.State
import kotlinx.coroutines.flow.SharedFlow

interface VMDelegation<EFFECT : Effect, EVENT : Event, STATE : State> {
    fun viewModel(viewModel: ViewModel)

    fun setEffect(effect: EFFECT)

    fun setEvent(event: EVENT)
    fun getCurrentState(): STATE

    fun setState(state: STATE)
    val effect: SharedFlow<EFFECT>
    val event: SharedFlow<EVENT>
    val state: SharedFlow<STATE>
}