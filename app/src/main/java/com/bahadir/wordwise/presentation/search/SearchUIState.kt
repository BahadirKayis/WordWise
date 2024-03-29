package com.bahadir.wordwise.presentation.search

import com.bahadir.wordwise.base.State

data class SearchUIState(
    val isLoading: Boolean = false,
    val lastSearchedWords: List<String>? = null
) : State