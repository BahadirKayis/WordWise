package com.bahadir.wordle.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.delegation.viewmodel.VMDelegation
import com.bahadir.wordle.delegation.viewmodel.VMDelegationImpl
import com.bahadir.wordle.dispatcher.DispatcherProvider
import com.bahadir.wordle.domain.usecase.AddWordUseCase
import com.bahadir.wordle.domain.usecase.LastSearchedWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val lastSearchUseCase: LastSearchedWordsUseCase,
    private val addWordsUseCase: AddWordUseCase,
    private val dispatcher: DispatcherProvider

) : ViewModel(),
    VMDelegation<SearchUIEffect, SearchEvent, SearchUIState> by VMDelegationImpl(SearchUIState()) {
    private var lastSearchedWords: List<String> = emptyList()

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is SearchEvent.ActionToSearch -> {
                    search(event.words)
                }

                is SearchEvent.ShowToastError -> {
                    setEffect(SearchUIEffect.ShowSnackError(event.message))
                }
            }
        }
       getLastSearchedWords()
    }

    private fun search(word: String) {
        if (word.isNotEmpty()) {
            viewModelScope.launch(dispatcher.io) {
                addWordsUseCase(word, lastSearchedWords)
            }
            setEffect(SearchUIEffect.ActionToSearch(word))
        } else setEffect(SearchUIEffect.ShowSnackError("Empty Error"))

    }

    private fun getLastSearchedWords() {
        lastSearchUseCase().flowOn(dispatcher.io).onEach {
            lastSearchedWords = it
            setState(SearchUIState(isLoading = false, lastSearchedWords = it))
        }.launchIn(viewModelScope)
    }
}
