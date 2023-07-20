package com.bahadir.wordwise.domain.source

interface DataStoreDataSource {
    suspend fun getLastSearchedWords(): MutableList<String>
    suspend fun addSearchedWord(word: String, lastSearchedWords: MutableList<String>)
}