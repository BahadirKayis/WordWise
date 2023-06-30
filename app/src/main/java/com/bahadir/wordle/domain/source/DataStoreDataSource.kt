package com.bahadir.wordle.domain.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DataStoreDataSource {
    val Context.dataStore: DataStore<Preferences>
    suspend fun getLastSearchedWords(): MutableList<String>
    suspend fun addSearchedWord(word: String, lastSearchedWords: MutableList<String>)
}