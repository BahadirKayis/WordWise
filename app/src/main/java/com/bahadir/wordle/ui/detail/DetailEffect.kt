package com.bahadir.wordle.ui.detail

import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.ui.base.Effect

sealed class DetailEffect : Effect {
    data class WordInformation(val word: WordsUI) : DetailEffect()
    data class ShowError(val message: String) : DetailEffect()
}