package com.bahadir.wordle.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.delegation.viewmodel.VMDelegation
import com.bahadir.wordle.delegation.viewmodel.VMDelegationImpl
import com.bahadir.wordle.domain.usecase.AddWordsUseCase
import com.bahadir.wordle.domain.usecase.LastSearchedWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val searchUseCase: LastSearchedWordsUseCase,
    private val addWordsUseCase: AddWordsUseCase
) : ViewModel(),
    VMDelegation<SearchUIEffect, SearchEvent, SearchUIState> by VMDelegationImpl(SearchUIState.SearchLoadingState()) {
    private lateinit var lastSearchedWords: List<String>

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
    }

    fun search(word: String) {
        if (word.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                addWordsUseCase(word, lastSearchedWords)
            }

            setEffect(SearchUIEffect.ActionToSearch(word))
        } else setEffect(SearchUIEffect.ShowSnackError("Empty Error"))

    }

    fun getLastSearchedWords() {
        searchUseCase().onEach {
            lastSearchedWords = it
            setState(SearchUIState.LastSearchedWords(it))
        }.launchIn(viewModelScope)
    }
}
