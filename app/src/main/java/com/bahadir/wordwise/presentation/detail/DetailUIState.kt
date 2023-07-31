package com.bahadir.wordwise.presentation.detail

import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.domain.model.WordsUI
import com.bahadir.wordwise.base.State

data class DetailUIState(
    val isLoading: Boolean = true,
    val synonym: List<SynonymsItem>? = null,
    val word: WordsUI? = null,
    val filter: CharSequence? = null
) : State