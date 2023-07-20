package com.bahadir.wordwise.domain.source

import com.bahadir.wordwise.data.model.synonyms.SynonymsItem

interface SynonymsDataSource {
    suspend fun getSynonyms(word: String): List<SynonymsItem>
}