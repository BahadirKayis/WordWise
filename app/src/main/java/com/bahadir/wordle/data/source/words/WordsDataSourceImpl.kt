package com.bahadir.wordle.data.source.words

import com.bahadir.wordle.data.model.words.WordsItem
import com.bahadir.wordle.domain.source.WordsDataSource

class WordsDataSourceImpl(private val wordsService: WordsService) : WordsDataSource {
    override suspend fun getWords(words: String): List<WordsItem> = wordsService.getWords(words)
}
