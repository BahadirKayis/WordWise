package com.bahadir.wordwise.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bahadir.wordwise.data.source.datastore.DataStoreDataSourceImpl
import com.bahadir.wordwise.data.source.synonyms.SynonymsDataSourceImpl
import com.bahadir.wordwise.data.source.synonyms.SynonymsService
import com.bahadir.wordwise.data.source.words.WordsDataSourceImpl
import com.bahadir.wordwise.data.source.words.WordsService
import com.bahadir.wordwise.domain.source.DataStoreDataSource
import com.bahadir.wordwise.domain.source.SynonymsDataSource
import com.bahadir.wordwise.domain.source.WordsDataSource
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
    fun providesDataStoreDataSource(dataStore: DataStore<Preferences>): DataStoreDataSource =
        DataStoreDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    private val Context.dataStore by preferencesDataStore(name = "com.bahadir.wordle")

}
