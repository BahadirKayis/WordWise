package com.bahadir.wordwise.data.source

import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.common.Constants.WORDS
import com.bahadir.wordwise.data.source.words.WordsDataSourceImpl
import com.bahadir.wordwise.data.source.words.WordsService
import com.bahadir.wordwise.wordsItem
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class WordSourceTest {

    @MockK
    private lateinit var wordsService: WordsService

    private lateinit var wordSource: WordsDataSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        wordSource = WordsDataSourceImpl(wordsService = wordsService)
    }


    @Test
    fun `getWords should return list of words`() = runBlocking {
        coEvery { wordsService.getWords(WORDS) } returns listOf(wordsItem)
        val response = wordSource.getWords(WORDS)
        Truth.assertThat(response?.first()?.word).isEqualTo(WORD)
    }

    @Test
    fun `getWords should return empty list on error`() = runBlocking {
        coEvery { wordsService.getWords(WORDS) } returns emptyList()
        val response = wordSource.getWords(WORDS)
        Truth.assertThat(response).isEmpty()
    }
    @After
    fun tearDown() {
        unmockkAll()
    }
}