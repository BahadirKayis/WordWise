package com.bahadir.wordwise.presentation.home

import app.cash.turbine.test
import com.bahadir.wordwise.TestDispatcher
import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.domain.repository.WordsRepository
import com.bahadir.wordwise.domain.usecase.AddWordUseCase
import com.bahadir.wordwise.domain.usecase.LastSearchedWordsUseCase
import com.bahadir.wordwise.lastSearchedList
import com.bahadir.wordwise.presentation.search.SearchEvent
import com.bahadir.wordwise.presentation.search.SearchUIEffect
import com.bahadir.wordwise.presentation.search.SearchUIState
import com.bahadir.wordwise.presentation.search.SearchVM
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class SearchVMTest {
    @MockK(relaxUnitFun = true)
    private lateinit var repository: WordsRepository
    private lateinit var lastSearchUseCase: LastSearchedWordsUseCase
    private lateinit var addWordUseCase: AddWordUseCase
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var viewModel: SearchVM

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        testDispatcher = TestDispatcher()
        Dispatchers.setMain(testDispatcher.testDispatcher)
        lastSearchUseCase = LastSearchedWordsUseCase(repository)
        addWordUseCase = AddWordUseCase(repository)
        viewModel = SearchVM(lastSearchUseCase, addWordUseCase, testDispatcher)

    }

    @Test
    fun `init should setup event handling correctly`() = runTest {
        viewModel.event.test {
            viewModel.setEvent(SearchEvent.ActionToSearch(WORD))
            assertThat(awaitItem()).isEqualTo(SearchEvent.ActionToSearch(WORD))

            viewModel.setEvent(SearchEvent.ShowToastError("error"))
            assertThat(awaitItem()).isEqualTo(SearchEvent.ShowToastError("error"))
        }
    }

    @Test
    fun `search function should show snack error when word is empty`() {
        runTest {
            viewModel.effect.test {
                viewModel.setEvent(SearchEvent.ActionToSearch(""))
                assertThat(awaitItem()).isEqualTo(SearchUIEffect.ShowSnackError("Empty Error"))
            }
        }
    }

    @Test
    fun `search function should add word and trigger search effect when word is not empty`() =
        runTest {
            viewModel.effect.test {
                viewModel.setEvent(SearchEvent.ActionToSearch(WORD))
                coVerify { repository.addSearchedWord(WORD, emptyList()) }
                assertThat(awaitItem()).isEqualTo(SearchUIEffect.ActionToSearch(WORD))
            }
        }

    @Test
    fun `getLastSearchedWords should update lastSearchedWords and SearchUIState`() = runTest {
        coEvery { repository.getLastSearchedWords() } returns flowOf(lastSearchedList)
        viewModel.getLastSearchedWords()
        viewModel.state.test {
            assertThat(awaitItem())
                .isEqualTo(SearchUIState(isLoading = false, lastSearchedWords = lastSearchedList))
        }
    }

}