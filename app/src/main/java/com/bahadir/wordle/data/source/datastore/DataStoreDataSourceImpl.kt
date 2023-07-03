package com.bahadir.wordle.data.source.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bahadir.wordle.common.extensions.titleCaseFirstChar
import com.bahadir.wordle.domain.source.DataStoreDataSource
import kotlinx.coroutines.flow.first

class DataStoreDataSourceImpl(private val dataStore: DataStore<Preferences>) : DataStoreDataSource {
    override suspend fun getLastSearchedWords(): MutableList<String> {
        return dataStore.data.first()[LAST_SEARCHED]?.split(",")?.toMutableList()
            ?: mutableListOf()

    }

    override suspend fun addSearchedWord(word: String, lastSearchedWords: MutableList<String>) {
        if (!lastSearchedWords.contains(word.titleCaseFirstChar())) {
            if (lastSearchedWords.size > SEARCHED_SIZE) {
                lastSearchedWords.removeAt(0)
                lastSearchedWords.add(word.titleCaseFirstChar())
            } else {
                lastSearchedWords.add(word.titleCaseFirstChar())
            }

        } else {
            lastSearchedWords.remove(word.titleCaseFirstChar())
            lastSearchedWords.add(word.titleCaseFirstChar())
        }
        val string = lastSearchedWords.joinToString(separator = ",")
        dataStore.edit { edit ->
            edit[LAST_SEARCHED] = string
        }
    }

    companion object {
        private val LAST_SEARCHED = stringPreferencesKey("lastSearched")
        private const val SEARCHED_SIZE = 4
    }
}