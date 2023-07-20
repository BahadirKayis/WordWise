package com.bahadir.wordwise.di

import com.bahadir.wordwise.data.repository.WordsRepositoryImpl
import com.bahadir.wordwise.domain.repository.WordsRepository
import com.bahadir.wordwise.domain.source.DataStoreDataSource
import com.bahadir.wordwise.domain.source.SynonymsDataSource
import com.bahadir.wordwise.domain.source.WordsDataSource
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