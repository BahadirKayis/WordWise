package com.bahadir.wordle.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.common.extensions.dataStore
import com.bahadir.wordle.common.extensions.titleCaseFirstChar
import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.mapper.wordsUI
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.domain.repository.WordsRepository
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class WordsRepositoryImpl(
    private val wordsDataSource: WordsDataSource,
    private val synonymsDataSource: SynonymsDataSource,
    context: Context
) : WordsRepository {
    private val dataStore = context.dataStore

    override fun getWords(word: String): Flow<Resource<List<WordsUI>>> = flow {
        try {
            emit(Resource.Success(wordsDataSource.getWords(word).wordsUI()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getSynonyms(word: String): Flow<Resource<List<SynonymsItem>>> = flow {
        try {
            emit(
                Resource.Success(
                    synonymsDataSource.getSynonyms(word).sortedBy { it.score }.reversed()
                        .take(5)
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getLastSearchedWords(): Flow<MutableList<String>> = flow {
        val lateSearched = stringPreferencesKey("lateSearched")
        val valueList = dataStore.data.first()[lateSearched]?.split(",")?.toMutableList()
        valueList?.let {
            emit(it)
        } ?: emit(mutableListOf())
    }

    override suspend fun setWords(word: String) {
        val lateSearched = stringPreferencesKey("lateSearched")
        getLastSearchedWords().collect {

            if (!it.contains(word.titleCaseFirstChar())) {
                if (it.size > 4) {
                    it.removeAt(0)
                    it.add(word.titleCaseFirstChar())
                } else {
                    it.add(word.titleCaseFirstChar())
                }
                val string = it.toTypedArray().joinToString(",")
                dataStore.edit { edit ->
                    edit[lateSearched] = string
                }
            }
        }
    }
}
