package com.bahadir.wordwise.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.bahadir.wordwise.R
import com.bahadir.wordwise.TestDispatcher
import com.bahadir.wordwise.WORD
import com.bahadir.wordwise.common.Constants
import com.bahadir.wordwise.common.Resource
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.domain.model.WordsUI
import com.bahadir.wordwise.domain.repository.WordsRepository
import com.bahadir.wordwise.domain.usecase.SearchUseCase
import com.bahadir.wordwise.domain.usecase.SynonymsUseCase
import com.bahadir.wordwise.synonymsItemList
import com.bahadir.wordwise.wordsUIList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @Mock
    private lateinit var repository: WordsRepository
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var searchUseCase: SearchUseCase
    private lateinit var synonymsUseCase: SynonymsUseCase
    private lateinit var savedState: SavedStateHandle
    private lateinit var viewModel: DetailVM


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = TestDispatcher()
        Dispatchers.setMain(Dispatchers.Unconfined)
        searchUseCase = SearchUseCase(repository)
        synonymsUseCase = SynonymsUseCase(repository)
        savedState = SavedStateHandle().apply {
            set(Constants.STATE_KEY_WORD, WORD)
        }
    }

    @Test
    fun `testInit should Initialize ViewModel Correctly`() = runTest {
        viewModel = DetailVM(savedState, searchUseCase, synonymsUseCase, testDispatcher)

        viewModel.event.test {
            viewModel.setEvent(DetailEvent.Voice)
            assertThat(awaitItem()).isEqualTo(DetailEvent.Voice)

            viewModel.setEvent(DetailEvent.Filter("test", 1))
            assertThat(awaitItem()).isEqualTo(DetailEvent.Filter("test", 1))

            viewModel.setEvent(DetailEvent.FilterClear)
            assertThat(awaitItem()).isEqualTo(DetailEvent.FilterClear)

            viewModel.setEvent(DetailEvent.BackPress)
            assertThat(awaitItem()).isEqualTo(DetailEvent.BackPress)
        }
    }

//    @Test
//    fun `testDetails should Update Filter And State Correctly`() = runTest {
//        viewModel = DetailVM(savedState, searchUseCase, synonymsUseCase, testDispatcher)
//
//        viewModel.setEvent(DetailEvent.Filter("test", 1))
//        viewModel.state.test {
//            assertThat(awaitItem()).isEqualTo(
//                DetailUIState(
//                    filter = "test"
//                )
//            )
//        }
//
//        viewModel.setEvent(DetailEvent.Filter("test", -1))
//        viewModel.state.test {
//            assertThat(awaitItem()).isEqualTo(
//                DetailUIState(
//                    filter = ""
//                )
//            )
//        }
//    }

    @Test
    fun `searchWord should update state and set effect correctly for Success cases`() =
        runTest {
            val mockResponse = mockSearchResponse(searchResponse = true, synonymResponse = true)
            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(
                    DetailUIState(
                        synonym = mockResponse.second,
                        word = mockResponse.first,
                        isLoading = false
                    )
                )
            }
        }


    @Test
    fun `searchWord should update state and set effect  correctly for Search Error cases`() =
        runTest {
            val mockResponse =
                mockSearchResponse(searchResponse = true, synonymResponse = false)

            viewModel.effect.test {
                viewModel.setEffect(DetailUIEffect.Error(R.string.empty_result))
                assertThat(awaitItem()).isInstanceOf(DetailUIEffect.Error::class.java)
            }

            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(
                    DetailUIState(
                        isLoading = false, word = mockResponse.first
                    )
                )
            }
        }

    @Test
    fun `searchWord should update state and set effect correctly for Synonyms Error cases`() =
        runTest {
            val mockResponse =
                mockSearchResponse(searchResponse = false, synonymResponse = true)
            viewModel.effect.test {
                viewModel.setEffect(DetailUIEffect.Error(R.string.empty_result))
                assertThat(awaitItem()).isInstanceOf(DetailUIEffect.Error::class.java)
            }
            viewModel.state.test {
                assertThat(awaitItem()).isEqualTo(
                    DetailUIState(
                        isLoading = false, synonym = mockResponse.second
                    )
                )
            }
        }

    @Test
    fun `searchWord should set effect correctly for Search and Synonyms Error case`() =
        runTest {
            mockSearchResponse(searchResponse = false, synonymResponse = false)
            viewModel.effect.test {
                viewModel.setEffect(DetailUIEffect.Error(R.string.empty_result))
                assertThat(awaitItem()).isInstanceOf(DetailUIEffect.Error::class.java)
            }
        }

    private fun mockSearchResponse(
        searchResponse: Boolean, synonymResponse: Boolean
    ): Pair<WordsUI, List<SynonymsItem>> {
        val searchResult =
            if (searchResponse) Resource.Success(listOf(wordsUIList)) else Resource.Error("Error")
        val synonymResult =
            if (synonymResponse) Resource.Success(synonymsItemList) else Resource.Error("Error")

        whenever(repository.getSynonyms(WORD)).thenReturn(flowOf(synonymResult))
        whenever(repository.getWords(WORD)).thenReturn(flowOf(searchResult))
        viewModel = DetailVM(savedState, searchUseCase, synonymsUseCase, testDispatcher)
        return Pair(wordsUIList, synonymsItemList)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
