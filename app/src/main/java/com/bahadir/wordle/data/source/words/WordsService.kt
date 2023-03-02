package com.bahadir.wordle.data.source.words

import com.bahadir.wordle.common.Constants.WORDS
import com.bahadir.wordle.data.model.words.WordsItem
import retrofit2.http.GET
import retrofit2.http.Path

interface WordsService {
    @GET(WORDS)
    suspend fun getWords(@Path("words") words: String): List<WordsItem>
}