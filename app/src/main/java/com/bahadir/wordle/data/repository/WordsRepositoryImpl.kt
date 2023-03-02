package com.bahadir.wordle.data.repository

import android.content.Context
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.data.model.synonyms.SynonymsItem
import com.bahadir.wordle.domain.mapper.wordsUI
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.domain.repository.WordsRepository
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class WordsRepositoryImpl(
    private val wordsDataSource: WordsDataSource,
    private val synonymsDataSource: SynonymsDataSource,
    context: Context
) :
    WordsRepository {

    override fun getWords(word: String): Flow<Resource<List<WordsUI>>> = flow {
        try {
            val words = wordsDataSource.getWords(word)
            emit(Resource.Success(words.wordsUI()))
        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }

    override fun getSynonyms(word: String): Flow<Resource<List<SynonymsItem>>> = flow {

    }

    override fun getLateSearched(): Flow<List<String>> = flow {

        TODO("Not yet implemented")
    }

    override fun setWords(word: String) {
        TODO("Not yet implemented")

    }


}
