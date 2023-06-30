package com.bahadir.wordle.ui.detail

import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.ui.base.State

data class DetailUIState(
    val isLoading: Boolean = true,
    val synonym: List<SynonymsItem>? = null,
    val word: WordsUI? = null,
    val filter: CharSequence? = null
) : State