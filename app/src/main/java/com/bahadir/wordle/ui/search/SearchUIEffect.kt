package com.bahadir.wordle.ui.search

import com.bahadir.wordle.ui.base.Effect

sealed class SearchUIEffect : Effect {
    
    data class SearchToDetail(val word: String) : SearchUIEffect()

}