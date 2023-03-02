package com.bahadir.wordle.data.source.synonyms

import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.source.SynonymsDataSource

class SynonymsDataSourceImpl(private val synonymsService: SynonymsService) : SynonymsDataSource {
    override suspend fun getSynonyms(word: String): List<SynonymsItem> =
        synonymsService.getSynonyms(word)

}