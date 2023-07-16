package com.bahadir.wordle.presentation.home

import app.cash.turbine.test
import com.bahadir.wordle.TestDispatcher
import com.bahadir.wordle.WORD
import com.bahadir.wordle.domain.repository.WordsRepository
import com.bahadir.wordle.domain.usecase.AddWordUseCase
import com.bahadir.wordle.domain.usecase.LastSearchedWordsUseCase
import com.bahadir.wordle.lastSearchedList
import com.bahadir.wordle.presentation.search.SearchEvent
import com.bahadir.wordle.presentation.search.SearchUIEffect
import com.bahadir.wordle.presentation.search.SearchUIState
import com.bahadir.wordle.presentation.search.SearchVM
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SearchVMTest {
    @Mock
    private lateinit var repository: WordsRepository
    private lateinit var lastSearchUseCase: LastSearchedWordsUseCase
    private lateinit var addWordUseCase: AddWordUseCase
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var viewModel: SearchVM

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
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
                verify(repository).addSearchedWord(WORD, emptyList())
                assertThat(awaitItem()).isEqualTo(SearchUIEffect.ActionToSearch(WORD))
            }
        }

    @Test
    fun `getLastSearchedWords should update lastSearchedWords and SearchUIState`() = runTest {
        `when`(repository.getLastSearchedWords()).thenReturn(flowOf(lastSearchedList))
        viewModel = SearchVM(lastSearchUseCase, addWordUseCase, testDispatcher)
        viewModel.state.test {
            assertThat(awaitItem())
                .isEqualTo(SearchUIState(isLoading = false, lastSearchedWords = lastSearchedList))
        }
    }

}