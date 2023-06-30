package com.bahadir.wordle.di

import com.bahadir.wordle.data.repository.WordsRepositoryImpl
import com.bahadir.wordle.domain.repository.WordsRepository
import com.bahadir.wordle.domain.source.DataStoreDataSource
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWordRepository(
        wordsDataSource: WordsDataSource,
        synonymsDataSource: SynonymsDataSource,
        dataStore: DataStoreDataSource
    ): WordsRepository = WordsRepositoryImpl(wordsDataSource, synonymsDataSource, dataStore)
}