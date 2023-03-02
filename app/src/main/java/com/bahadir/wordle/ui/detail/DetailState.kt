package com.bahadir.wordle.ui.detail

import com.bahadir.wordle.ui.base.State

sealed class DetailState : State {
    data class DetailLoadingState(val loadingState: Boolean = false) : DetailState()
}