package com.bahadir.wordwise.domain.source

import com.bahadir.wordwise.data.model.words.WordsItem

interface WordsDataSource {
    suspend fun getWords(words: String): List<WordsItem>
}