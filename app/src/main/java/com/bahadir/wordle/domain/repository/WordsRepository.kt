package com.bahadir.wordle.domain.repository

import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.model.WordsUI
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    fun getWords(word: String): Flow<Resource<List<WordsUI>>>

    fun getSynonyms(word: String): Flow<Resource<List<SynonymsItem>>>

    fun getLastSearchedWords(): Flow<List<String>>

    suspend fun addSearchedWord(word: String, lastSearch: List<String>)
}