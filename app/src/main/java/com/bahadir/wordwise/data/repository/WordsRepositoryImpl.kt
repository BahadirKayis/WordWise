package com.bahadir.wordwise.data.repository

import com.bahadir.wordwise.common.Resource
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.domain.mapper.wordsUI
import com.bahadir.wordwise.domain.model.WordsUI
import com.bahadir.wordwise.domain.repository.WordsRepository
import com.bahadir.wordwise.domain.source.DataStoreDataSource
import com.bahadir.wordwise.domain.source.SynonymsDataSource
import com.bahadir.wordwise.domain.source.WordsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class WordsRepositoryImpl(
    private val wordsDataSource: WordsDataSource,
    private val synonymsDataSource: SynonymsDataSource,
    private val dataSource: DataStoreDataSource,
) : WordsRepository {
    override fun getWords(word: String): Flow<Resource<List<WordsUI>>> = flow {
        val data = wordsDataSource.getWords(word).wordsUI()
        emit(Resource.Success(data))
    }.catch {
        Resource.Error(it.message.toString())
    }

    override fun getSynonyms(word: String): Flow<Resource<List<SynonymsItem>>> = flow {
        val data = synonymsDataSource.getSynonyms(word).sortedBy { it.score }.reversed()
            .take(5)
        emit(Resource.Success(data))
    }.catch { Resource.Error(it.message.toString()) }

    override fun getLastSearchedWords(): Flow<MutableList<String>> = flow {
        emit(dataSource.getLastSearchedWords())
    }

    override suspend fun addSearchedWord(word: String, lastSearch: List<String>) {
        dataSource.addSearchedWord(word, lastSearch.toMutableList())
    }

}
