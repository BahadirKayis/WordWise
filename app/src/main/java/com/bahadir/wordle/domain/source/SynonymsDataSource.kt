package com.bahadir.wordle.domain.source

import com.bahadir.wordle.data.model.synonyms.SynonymsItem

interface SynonymsDataSource {
    suspend fun getSynonyms(word: String): List<SynonymsItem>
}