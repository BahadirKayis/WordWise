package com.bahadir.wordwise.data.source.words

import com.bahadir.wordwise.common.Constants.WORDS
import com.bahadir.wordwise.data.model.words.WordsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface WordsService {
    @GET(WORDS)
    suspend fun getWords(@Path("words") words: String): List<WordsItem>?
}