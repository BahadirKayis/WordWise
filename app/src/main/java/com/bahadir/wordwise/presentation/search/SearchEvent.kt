package com.bahadir.wordwise.presentation.search

import com.bahadir.wordwise.base.Event

sealed class SearchEvent : Event {
    data class ActionToSearch(val words: String) : SearchEvent()
    data class ShowToastError(val message: String) : SearchEvent()
}