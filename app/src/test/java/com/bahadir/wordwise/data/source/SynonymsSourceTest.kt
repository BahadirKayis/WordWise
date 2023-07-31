package com.bahadir.wordwise.data.source

import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordwise.data.source.synonyms.SynonymsService
import com.bahadir.wordwise.synonymsItemList
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SynonymsSourceTest {

    @MockK
    private lateinit var synonymsService: SynonymsService

    private lateinit var synonymsSource: SynonymsDataSourceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        synonymsSource = SynonymsDataSourceImpl(synonymsService = synonymsService)
    }

    @Test
    fun `getSynonyms should return list of synonyms`() = runBlocking {
        coEvery { synonymsService.getSynonyms(WORD) } returns synonymsItemList
        val response = synonymsSource.getSynonyms(WORD)
        assertThat(response).isNotEmpty()
    }

    @Test
    fun `getSynonyms should return empty list on error`() = runBlocking {
        coEvery { synonymsService.getSynonyms(WORD) } returns emptyList()
        val response = synonymsSource.getSynonyms(WORD)
        assertThat(response).isEmpty()
    }
    @After
    fun tearDown() {
        unmockkAll()
    }
}