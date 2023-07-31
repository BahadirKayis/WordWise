package com.bahadir.wordwise.data.api

import com.bahadir.wordwise.SYNONYM_RESPONSE_NAME
import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.WORD_RESPONSE_NAME
import com.bahadir.wordwise.data.source.synonyms.SynonymsService
import com.bahadir.wordwise.data.source.words.WordsService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {
    private val mockWebServer: MockWebServer = MockWebServer()
    private lateinit var wordApi: WordsService
    private lateinit var synonymsApi: SynonymsService

    @Before
    fun setUp() {
        mockWebServer.start()
        wordApi = Retrofit.Builder().baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WordsService::class.java)
        synonymsApi = Retrofit.Builder().baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SynonymsService::class.java)
    }

    //WordApi Tests

    @Test
    fun `response when word searched is not null`() {
        runBlocking {
            enqueueMockResponse(WORD_RESPONSE_NAME)
            val response = wordApi.getWords(WORD)
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenWordRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(WORD_RESPONSE_NAME)
            wordApi.getWords(WORD)
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/entries/en/$WORD")
        }
    }

    @Test
    fun firstElement_whenWordRequested_hasSameName() {
        runBlocking {
            enqueueMockResponse(WORD_RESPONSE_NAME)
            val response = wordApi.getWords(WORD)?.first()
            mockWebServer.takeRequest()
            assertThat(response?.word).isEqualTo(WORD)
        }
    }

    //SYNONYM Tests
    @Test
    fun response_whenSynonymsSearched_isNotNull() {
        runBlocking {
            enqueueMockResponse(SYNONYM_RESPONSE_NAME)
            val response = synonymsApi.getSynonyms(WORD)
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenSynonymsRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(SYNONYM_RESPONSE_NAME)
            synonymsApi.getSynonyms(WORD)
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/words?rel_syn=$WORD")
        }
    }

    @Test
    fun firstElement_whenSynonymsRequested_hasSameName() {
        runBlocking {
            enqueueMockResponse(SYNONYM_RESPONSE_NAME)
            val response = synonymsApi.getSynonyms(WORD)?.first()
            mockWebServer.takeRequest()
            assertThat(response?.word).isEqualTo("house")
        }
    }


    private fun enqueueMockResponse(serverResponseFileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(serverResponseFileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}