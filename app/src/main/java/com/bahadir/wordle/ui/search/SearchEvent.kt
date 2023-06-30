package com.bahadir.wordle.ui.search

import com.bahadir.wordle.ui.base.Event

sealed class SearchEvent : Event {
    data class ActionToSearch(val words: String) : SearchEvent()
    data class ShowToastError(val message: String) : SearchEvent()
}