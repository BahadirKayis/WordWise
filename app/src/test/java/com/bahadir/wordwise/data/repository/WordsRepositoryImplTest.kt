package com.bahadir.wordwise.data.repository

import com.bahadir.wordwise.ADD_WORD
import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.common.Resource
import com.bahadir.wordwise.common.extensions.titleCaseFirstChar
import com.bahadir.wordwise.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordwise.data.source.synonyms.SynonymsService
import com.bahadir.wordwise.data.source.words.WordsDataSourceImpl
import com.bahadir.wordwise.data.source.words.WordsService
import com.bahadir.wordwise.domain.repository.WordsRepository
import com.bahadir.wordwise.domain.source.DataStoreDataSource
import com.bahadir.wordwise.domain.source.SynonymsDataSource
import com.bahadir.wordwise.domain.source.WordsDataSource
import com.bahadir.wordwise.lastSearchedList
import com.bahadir.wordwise.synonymsItemList
import com.bahadir.wordwise.wordResponse
import com.bahadir.wordwise.wordsItem
import com.bahadir.wordwise.wordsUIList
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class WordsRepositoryImplTest {
    @MockK
    private lateinit var wordsService: WordsService

    @MockK
    private lateinit var synonymsService: SynonymsService

    private lateinit var wordSource: WordsDataSource
    private lateinit var synonymsSource: SynonymsDataSource
    private lateinit var dataStoreSource: DataStoreDataSource
    private lateinit var wordsRepository: WordsRepository


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        wordSource = WordsDataSourceImpl(wordsService = wordsService)
        synonymsSource = SynonymsDataSourceImpl(synonymsService = synonymsService)
        dataStoreSource = mockk<DataStoreDataSource>(relaxed = true)
        wordsRepository = WordsRepositoryImpl(
            wordsDataSource = wordSource,
            synonymsDataSource = synonymsSource,
            dataSource = dataStoreSource
        )
    }

    @Test
    fun `getWords should return success`() {
        runBlocking {
            coEvery { wordsService.getWords(WORD) } returns wordResponse
            val state = wordsRepository.getWords(WORD).first()
            assertThat(state).isInstanceOf(Resource.Success::class.java)
        }
    }

    @Test
    fun `getWords should return error`() {
        runTest {
            coEvery { wordsService.getWords(WORD) } returns null
            val state = wordsRepository.getWords(WORD).first()
            assertThat(state).isInstanceOf(Resource.Error::class.java)
        }
    }


    @Test
    fun getWords_should_map() {
        runBlocking {
            coEvery { wordsService.getWords(WORD) } returns listOf(wordsItem)
            val state = wordsRepository.getWords(WORD).first()
            println(state)
            assertThat(state).isEqualTo(Resource.Success(listOf(wordsUIList)))
        }
    }

    @Test
    fun `getSynonyms should return success`() {
        runBlocking {
            coEvery { synonymsService.getSynonyms(WORD) } returns (synonymsItemList)
            val state = wordsRepository.getSynonyms(WORD).first()
            assertThat(state).isInstanceOf(Resource.Success::class.java)
        }
    }

    @Test
    fun `getSynonyms should return error`() {
        runBlocking {
            coEvery { synonymsService.getSynonyms(WORD) } returns null
            val state = wordsRepository.getSynonyms(WORD).first()
            assertThat(state).isInstanceOf(Resource.Error::class.java)
        }
    }

    @Test
    fun `getLastSearchedWords should emit correct list`() = runBlocking {
        coEvery { dataStoreSource.getLastSearchedWords() } returns lastSearchedList
        val result = wordsRepository.getLastSearchedWords().first()
        assertThat(result).isEqualTo(lastSearchedList)
    }

    @Test
    fun `getLastSearchedWords EmptyTest`() = runBlocking {
        coEvery { dataStoreSource.getLastSearchedWords() } returns (mutableListOf())
        val result = wordsRepository.getLastSearchedWords().first()
        coVerify { dataStoreSource.getLastSearchedWords() }
        assertThat(result).isEmpty()
    }

    @Test
    fun `addSearchedWord should add word to last searched words`() = runTest {
        val addedList = lastSearchedList.plus(ADD_WORD).toMutableList()
        coEvery { (dataStoreSource.getLastSearchedWords()) } returns addedList
        wordsRepository.addSearchedWord(ADD_WORD, lastSearchedList)
        coVerify { dataStoreSource.addSearchedWord(ADD_WORD, lastSearchedList) }
        assertThat(dataStoreSource.getLastSearchedWords()).contains(ADD_WORD.titleCaseFirstChar())
    }

    @Test
    fun `addSearchedWord should not add word if it already exists in last searched words`() =
        runBlocking {
            coEvery { (dataStoreSource.getLastSearchedWords()) } returns lastSearchedList
            wordsRepository.addSearchedWord(WORD, lastSearchedList)
            coVerify { dataStoreSource.addSearchedWord(WORD, lastSearchedList) }
            assertThat(dataStoreSource.getLastSearchedWords()).containsExactlyElementsIn(
                lastSearchedList
            ).inOrder()
        }

}

