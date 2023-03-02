package com.bahadir.wordle.domain.source

import com.bahadir.wordle.data.model.words.WordsItem

interface WordsDataSource {
    suspend fun getWords(words: String): List<WordsItem>
}