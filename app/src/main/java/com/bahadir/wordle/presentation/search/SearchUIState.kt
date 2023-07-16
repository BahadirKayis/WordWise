package com.bahadir.wordle.presentation.search

import com.bahadir.wordle.presentation.base.State

data class SearchUIState(
    val isLoading: Boolean = false,
    val lastSearchedWords: List<String>? = null
) : State