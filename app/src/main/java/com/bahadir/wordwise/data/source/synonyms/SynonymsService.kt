package com.bahadir.wordwise.data.source.synonyms

import com.bahadir.wordwise.common.Constants.SYNONYMS
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import retrofit2.http.GET
import retrofit2.http.Query

interface SynonymsService {
    @GET(SYNONYMS)
    suspend fun getSynonyms(@Query("rel_syn") words: String): List<SynonymsItem>?
}