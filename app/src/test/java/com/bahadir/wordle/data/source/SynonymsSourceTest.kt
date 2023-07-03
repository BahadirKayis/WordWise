package com.bahadir.wordle.data.source

import com.bahadir.wordle.WORD
import com.bahadir.wordle.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordle.data.source.synonyms.SynonymsService
import com.bahadir.wordle.synonymsItemList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SynonymsSourceTest {

    @Mock
    private lateinit var synonymsService: SynonymsService

    private lateinit var synonymsSource: SynonymsDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        synonymsSource = SynonymsDataSourceImpl(synonymsService = synonymsService)
    }

    @Test
    fun `getSynonyms should return list of synonyms`() = runBlocking {
        Mockito.`when`(synonymsService.getSynonyms(WORD)).thenReturn(synonymsItemList)
        val response = synonymsSource.getSynonyms(WORD)
        assertThat(response).isNotEmpty()
    }

    @Test
    fun `getSynonyms should return empty list on error`() = runBlocking {
        Mockito.`when`(synonymsService.getSynonyms(WORD)).thenReturn(emptyList())
        val response = synonymsSource.getSynonyms(WORD)
        assertThat(response).isEmpty()
    }
}