package com.bahadir.wordwise.data.source.synonyms

import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.domain.source.SynonymsDataSource

class SynonymsDataSourceImpl(private val synonymsService: SynonymsService) : SynonymsDataSource {
    override suspend fun getSynonyms(word: String): List<SynonymsItem>?=
        synonymsService.getSynonyms(word)
}