package com.bahadir.wordle.presentation.search

import com.bahadir.wordle.presentation.base.Event

sealed class SearchEvent : Event {
    data class ActionToSearch(val words: String) : SearchEvent()
    data class ShowToastError(val message: String) : SearchEvent()
}