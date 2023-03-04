package com.bahadir.wordle.ui.detail

import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.ui.base.State

sealed class DetailUIState : State {
    data class DetailLoadingUIState(val isLoading: Boolean = false) : DetailUIState()
    data class WordAndSynonym(val word: WordsUI, val synonym: List<SynonymsItem>) :
        DetailUIState()
    data class FilterShow(val filter: CharSequence) : DetailUIState()
}