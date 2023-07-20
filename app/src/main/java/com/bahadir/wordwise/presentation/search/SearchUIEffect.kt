package com.bahadir.wordwise.presentation.search

import com.bahadir.wordwise.presentation.base.Effect

sealed class SearchUIEffect : Effect {
    data class ActionToSearch(val word: String) : SearchUIEffect()
    data class ShowSnackError(val message: String) : SearchUIEffect()
}