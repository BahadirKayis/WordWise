package com.bahadir.wordwise.domain.repository

import com.bahadir.wordwise.common.Resource
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.domain.model.WordsUI
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    fun getWords(word: String): Flow<Resource<List<WordsUI>>>

    fun getSynonyms(word: String): Flow<Resource<List<SynonymsItem>>>

    fun getLastSearchedWords(): Flow<List<String>>

    suspend fun addSearchedWord(word: String, lastSearch: List<String>)
}