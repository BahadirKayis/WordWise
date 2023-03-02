package com.bahadir.wordle.delegation.viewmodel

import androidx.lifecycle.ViewModel
import com.bahadir.wordle.ui.base.Effect
import com.bahadir.wordle.ui.base.Event
import com.bahadir.wordle.ui.base.State
import kotlinx.coroutines.flow.SharedFlow

interface VMDelegation<EFFECT : Effect, EVENT : Event, STATE : State> {
    fun viewModel(viewModel: ViewModel)

     fun setEffect(effect: EFFECT)

    fun setEvent(event: EVENT)

    fun setState(state: STATE)
    val effect: SharedFlow<EFFECT>
    val event: SharedFlow<EVENT>
    val state: SharedFlow<STATE>

}