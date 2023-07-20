package com.bahadir.wordwise.di

import com.bahadir.wordwise.common.Constants.BASE_URL_SYNONYMS
import com.bahadir.wordwise.common.Constants.BASE_URL_WORD
import com.bahadir.wordwise.data.source.synonyms.SynonymsService
import com.bahadir.wordwise.data.source.words.WordsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideWordService(): WordsService = Retrofit.Builder()
        .baseUrl(BASE_URL_WORD)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WordsService::class.java)

    @Provides
    @Singleton
    fun provideSynonymsService(): SynonymsService = Retrofit.Builder()
        .baseUrl(BASE_URL_SYNONYMS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SynonymsService::class.java)
}