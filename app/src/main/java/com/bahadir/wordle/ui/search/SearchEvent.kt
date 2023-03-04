package com.bahadir.wordle.ui.search

import com.bahadir.wordle.ui.base.Event

sealed class SearchEvent : Event {
    data class Search(val word: String) : SearchEvent()
    data class ShowToastError(val message: String) : SearchEvent()
}