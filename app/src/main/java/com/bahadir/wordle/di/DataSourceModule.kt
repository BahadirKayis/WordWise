package com.bahadir.wordle.di

import com.bahadir.wordle.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordle.data.source.synonyms.SynonymsService
import com.bahadir.wordle.data.source.words.WordsDataSourceImpl
import com.bahadir.wordle.data.source.words.WordsService
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun providesWordsDataSource(wordsService: WordsService): WordsDataSource =
        WordsDataSourceImpl(wordsService)

    @Provides
    @Singleton
    fun providesSynonymsDataSource(synonymsService: SynonymsService): SynonymsDataSource =
        SynonymsDataSourceImpl(synonymsService)
}
