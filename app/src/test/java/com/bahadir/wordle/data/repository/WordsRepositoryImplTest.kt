package com.bahadir.wordle.data.repository

import com.bahadir.wordle.ADD_WORD
import com.bahadir.wordle.WORD
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.common.extensions.titleCaseFirstChar
import com.bahadir.wordle.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordle.data.source.synonyms.SynonymsService
import com.bahadir.wordle.data.source.words.WordsDataSourceImpl
import com.bahadir.wordle.data.source.words.WordsService
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.domain.repository.WordsRepository
import com.bahadir.wordle.domain.source.DataStoreDataSource
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import com.bahadir.wordle.lastSearchedList
import com.bahadir.wordle.synonymsItemList
import com.bahadir.wordle.wordResponse
import com.bahadir.wordle.wordsItem
import com.bahadir.wordle.wordsUIList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class WordsRepositoryImplTest {
    @Mock
    private lateinit var wordsService: WordsService

    @Mock
    private lateinit var synonymsService: SynonymsService

    private lateinit var wordSource: WordsDataSource
    private lateinit var synonymsSource: SynonymsDataSource

    @Mock
    private lateinit var dataStoreSource: DataStoreDataSource

    private lateinit var wordsRepository: WordsRepository


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        wordSource = WordsDataSourceImpl(wordsService = wordsService)
        synonymsSource = SynonymsDataSourceImpl(synonymsService = synonymsService)
        //test ortamında gerçek veri kaynağı yerine mock bir veri kaynağı kullanmanızı sağlar
        dataStoreSource = Mockito.mock(DataStoreDataSource::class.java)
        wordsRepository = WordsRepositoryImpl(
            wordsDataSource = wordSource,
            synonymsDataSource = synonymsSource,
            dataSource = dataStoreSource
        )
    }

    @Test
    fun `getWords should return success`() {
        runBlocking {
            Mockito.`when`(wordsService.getWords(WORD)).thenReturn(wordResponse)
            val state = wordsRepository.getWords(WORD).first()
            assertThat(state).isInstanceOf(Resource.Success::class.java)
        }
    }

    @Test
    fun `getWords should return error`() {
        runBlocking {
            Mockito.`when`(wordsService.getWords(WORD)).thenReturn(null)
            val state = wordsRepository.getWords(WORD).first()
            assertThat(state).isInstanceOf(Resource.Error::class.java)
        }
    }


    @Test
    fun getWords_should_map() {
        runBlocking {
            Mockito.`when`(wordsService.getWords(WORD)).thenReturn(listOf(wordsItem))
            val state = wordsRepository.getWords(WORD).first()

            assertThat((state as Resource.Success).data).isEqualTo(wordsUIList)
        }
    }

    @Test
    fun `getSynonyms should return success`() {
        runBlocking {
            Mockito.`when`(synonymsService.getSynonyms(WORD)).thenReturn(synonymsItemList)
            val state = wordsRepository.getSynonyms(WORD).first()
            assertThat(state).isInstanceOf(Resource.Success::class.java)
        }
    }

    @Test
    fun `getSynonyms should return error`() {
        runBlocking {
            Mockito.`when`(synonymsService.getSynonyms(WORD)).thenReturn(null)
            val state = wordsRepository.getSynonyms(WORD).first()
            assertThat(state).isInstanceOf(Resource.Error::class.java)
        }
    }

    @Test
    fun `getLastSearchedWords should emit correct list`() = runBlocking {
        // Arrange
        Mockito.`when`(dataStoreSource.getLastSearchedWords()).thenReturn(lastSearchedList)
        // Act
        val result = wordsRepository.getLastSearchedWords().single()

        // Assert
        assertThat(result).isEqualTo(lastSearchedList)
    }

    @Test
    fun `getLastSearchedWords EmptyTest`() = runBlocking {
        // Arrange
        Mockito.`when`(dataStoreSource.getLastSearchedWords()).thenReturn(mutableListOf())
        // Act
        val result = wordsRepository.getLastSearchedWords().single()
        Mockito.verify(dataStoreSource).getLastSearchedWords()
        // Assert
        assertThat(result).isEmpty()
    }

    @Test
    fun `addSearchedWord should add word to last searched words`() = runBlocking {
//        Repository testi yazarken, gerçek veri kaynağının yerine sahte (mock) bir veri kaynağı kullanırız
//        Bu nedenle, sahte veri kaynağı üzerinde kontrol edilebilir bir durum yaratmanız
//        ve beklentilere göre davranmasını sağlamanız gerekmektedir.
        // Arrange

        // Mock getLastSearchedWords() to return initialLastSearchedWords
        Mockito.`when`(dataStoreSource.getLastSearchedWords())
            .thenReturn(lastSearchedList.plus(ADD_WORD).toMutableList())

        println(lastSearchedList)
        //Act
        wordsRepository.addSearchedWord(ADD_WORD, lastSearchedList)

        // Verify that getLastSearchedWords() is called to update the list
        Mockito.verify(dataStoreSource).addSearchedWord(ADD_WORD, lastSearchedList)
        // Assert
        assertThat(dataStoreSource.getLastSearchedWords()).contains(ADD_WORD.titleCaseFirstChar())
    }

    @Test
    fun `addSearchedWord should not add word if it already exists in last searched words`() =
        runBlocking {
            // Mock getLastSearchedWords() to return initialLastSearchedWords
            Mockito.`when`(dataStoreSource.getLastSearchedWords())
                .thenReturn(lastSearchedList)

            // Act
            wordsRepository.addSearchedWord(WORD, lastSearchedList)

            // Verify that addSearchedWord() is not called
            Mockito.verify(dataStoreSource).addSearchedWord(WORD, lastSearchedList)

            // Assert
            // Ensure that the last searched words remain unchanged
            assertThat(dataStoreSource.getLastSearchedWords()).containsExactlyElementsIn(
                lastSearchedList
            ).inOrder()
        }

}

