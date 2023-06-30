package com.bahadir.wordle.data.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bahadir.wordle.common.extensions.titleCaseFirstChar
import com.bahadir.wordle.domain.source.DataStoreDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSourceImpl(private val context: Context) : DataStoreDataSource {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "com.bahadir.last.searched")

    override suspend fun getLastSearchedWords(): MutableList<String> {
        return context.dataStore.data.first()[lastSearched]?.split(",")?.toMutableList()
            ?: mutableListOf()

    }

    override suspend fun addSearchedWord(word: String, lastSearchedWords: MutableList<String>) {
        if (!lastSearchedWords.contains(word.titleCaseFirstChar())) {
            if (lastSearchedWords.size > 4) {
                lastSearchedWords.removeAt(0)
                lastSearchedWords.add(word.titleCaseFirstChar())
            } else {
                lastSearchedWords.add(word.titleCaseFirstChar())
            }
            val string = lastSearchedWords.joinToString(separator = ",")
            context.dataStore.edit { edit ->
                edit[lastSearched] = string
            }
//            context.dataStore.updateData { preferences ->
//                preferences.toMutablePreferences().apply {
//                    this[lastSearched] = string
//                }
//            }
        }

    }

    companion object {
        private val lastSearched = stringPreferencesKey("lateSearched")
    }
}