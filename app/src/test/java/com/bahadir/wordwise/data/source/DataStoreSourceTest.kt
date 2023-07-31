package com.bahadir.wordwise.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bahadir.wordwise.data.source.datastore.DataStoreDataSourceImpl
import com.bahadir.wordwise.domain.source.DataStoreDataSource
import com.bahadir.wordwise.lastSearchedList
import com.bahadir.wordwise.lastSearchedString
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class DataStoreSourceTest {

    @MockK
    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var dataStoreSource: DataStoreDataSource

    @MockK
    private lateinit var emptyPreferences: Preferences

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { testDataStore.data } returns flowOf(emptyPreferences)
        coEvery { testDataStore.updateData(any()) } returns (emptyPreferences)
        dataStoreSource = DataStoreDataSourceImpl(testDataStore)
    }

    @Test
    fun `get last searched words should return list empty`() = runBlocking {
        coEvery { emptyPreferences[LAST_SEARCHED] } returns null
        val response = dataStoreSource.getLastSearchedWords()
        assertThat(response).isEmpty()
    }

    @Test
    fun `getLastSearchedWords should convert string to list`() = runBlocking {
        coEvery { emptyPreferences[LAST_SEARCHED] } returns lastSearchedString
        val response = dataStoreSource.getLastSearchedWords()
        assertThat(response).isInstanceOf(List::class.java)
    }

    @Test
    fun `getLastSearchedWords should return list of words`() = runBlocking {
        coEvery { emptyPreferences[LAST_SEARCHED] } returns lastSearchedString
        val response = dataStoreSource.getLastSearchedWords()
        assertThat(response).isNotEmpty()
    }

    @Test
    fun `addSearchedWord should add a new word to an empty list`() = runBlocking {
        val word = "Word"
        val list = mutableListOf<String>()
        dataStoreSource.addSearchedWord(word, list)
        assertThat(list).contains(word)
    }

    @Test
    fun `addSearchedWord should remove oldest word if list size is greater than 4`() = runBlocking {
        val word = "Word"
        val list = mutableListOf("Home", "House", "Family", "Base", "School")
        dataStoreSource.addSearchedWord(word, list)
        assertThat(list).containsExactly("House", "Family", "Base", "School", "Word").inOrder()
    }

    @Test
    fun `addSearchedWord should add word to the end if list size is less than or equal to 4`() =
        runBlocking {
            val word = "Word"
            dataStoreSource.addSearchedWord(word, lastSearchedList)
            assertThat(lastSearchedList.indexOf(word)).isEqualTo(4)
        }

    @Test
    fun `addSearchedWord should move existing word to the front if it already exists`() =
        runBlocking {
            val word = "Home"
            val lastIndex = lastSearchedList.indexOf(word)
            dataStoreSource.addSearchedWord(word, lastSearchedList)
            val newIndex = lastSearchedList.indexOf(word)
            assertThat(lastIndex).isNotEqualTo(newIndex)
        }

    @After
    fun tearDown() {
        unmockkAll()
    }

    companion object {
        private val LAST_SEARCHED = stringPreferencesKey("lastSearched")
    }
}