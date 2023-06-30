package com.bahadir.wordle.di

import android.content.Context
import com.bahadir.wordle.data.source.datastore.DataStoreDataSourceImpl
import com.bahadir.wordle.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordle.data.source.synonyms.SynonymsService
import com.bahadir.wordle.data.source.words.WordsDataSourceImpl
import com.bahadir.wordle.data.source.words.WordsService
import com.bahadir.wordle.domain.source.DataStoreDataSource
import com.bahadir.wordle.domain.source.SynonymsDataSource
import com.bahadir.wordle.domain.source.WordsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providesDataStoreDataSource(@ApplicationContext context: Context): DataStoreDataSource =
        DataStoreDataSourceImpl(context)

}
