package com.bahadir.wordle.data.source

import com.bahadir.wordle.WORD
import com.bahadir.wordle.common.Constants.WORDS
import com.bahadir.wordle.data.source.words.WordsDataSourceImpl
import com.bahadir.wordle.data.source.words.WordsService
import com.bahadir.wordle.wordsItem
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WordSourceTest {

    @Mock
    private lateinit var wordsService: WordsService

    private lateinit var wordSource: WordsDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        wordSource = WordsDataSourceImpl(wordsService = wordsService)
    }


    @Test
    fun `getWords should return list of words`() = runBlocking {
        Mockito.`when`(wordsService.getWords(WORDS)).thenReturn(listOf(wordsItem))
        val response = wordSource.getWords(WORDS)
        Truth.assertThat(response.first().word).isEqualTo(WORD)
    }

    @Test
    fun `getWords should return empty list on error`() = runBlocking {
        Mockito.`when`(wordsService.getWords(WORDS)).thenReturn(emptyList())
        val response = wordSource.getWords(WORDS)
        Truth.assertThat(response).isEmpty()
    }
}