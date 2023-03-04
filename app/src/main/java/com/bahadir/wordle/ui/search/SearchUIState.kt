package com.bahadir.wordle.ui.search

import com.bahadir.wordle.ui.base.State

sealed class SearchUIState : State {
    data class SearchLoadingState(val loadingState: Boolean = false) : SearchUIState()
    data class LastSearchedWords(val lastSearchedWords: List<String>) : SearchUIState()
}