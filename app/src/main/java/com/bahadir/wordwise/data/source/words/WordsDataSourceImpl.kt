package com.bahadir.wordwise.data.source.words

import com.bahadir.wordwise.data.model.words.WordsItem
import com.bahadir.wordwise.domain.source.WordsDataSource

class WordsDataSourceImpl(private val wordsService: WordsService) : WordsDataSource {
    override suspend fun getWords(words: String): List<WordsItem>? = wordsService.getWords(words)
}
