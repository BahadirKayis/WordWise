package com.bahadir.wordle.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
    fun providesDataStoreDataSource(dataStore: DataStore<Preferences>): DataStoreDataSource =
        DataStoreDataSourceImpl(dataStore)

    @Provides
    @Singleton
    fun providesDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    private val Context.dataStore by preferencesDataStore(name = "com.bahadir.wordle")


    //bu şekilde kullanmamın herhangi bir sakıncası yok. Kotlin'de by anahtar kelimesi,
// bir özelliği (property) başka bir nesnenin üzerinden yönlendirmek için kullanılır. Bu durumda,
// preferencesDataStore işlevi, Context nesnesi üzerinden dataStore özelliğine erişim sağlar.
    //
    //dataStore özelliği, bir by delegasyonu olarak çalışır ve her erişildiğinde yeni bir DataStore
// örneği oluşturulmaz. Bunun yerine, preferencesDataStore işlevi yalnızca ilk erişimde bir kere çağrılır ve
// döndürülen DataStore örneği sonraki erişimlerde kullanılır. Bu şekilde, bellek sızıntısı veya performans
// sorunlarına neden olmaz.
    //
    //Bu kullanım, Kotlin'in by delegasyonunu verimli bir şekilde kullanmanızı sağlar ve kodunuzun
// daha okunabilir ve basit olmasını sağlar.
//    @Singleton
//    @Provides
//    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            corruptionHandler = ReplaceFileCorruptionHandler(
//                produceNewData = { emptyPreferences() }
//            ),
//            migrations = listOf(SharedPreferencesMigration(appContext, "com.bahadir.wordle")),
//            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            produceFile = { appContext.preferencesDataStoreFile("com.bahadir.wordle") }
//        )
//    }
}
