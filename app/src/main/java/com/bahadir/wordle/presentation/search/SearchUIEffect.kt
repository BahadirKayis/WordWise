package com.bahadir.wordle.presentation.search

import com.bahadir.wordle.presentation.base.Effect

sealed class SearchUIEffect : Effect {
    data class ActionToSearch(val word: String) : SearchUIEffect()
    data class ShowSnackError(val message: String) : SearchUIEffect()
}