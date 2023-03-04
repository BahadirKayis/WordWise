package com.bahadir.wordle.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.delegation.viewmodel.VMDelegation
import com.bahadir.wordle.delegation.viewmodel.VMDelegationImpl
import com.bahadir.wordle.domain.usecase.LastSearchedWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val search: LastSearchedWordsUseCase) : ViewModel(),
    VMDelegation<SearchUIEffect, SearchEvent, SearchUIState> by VMDelegationImpl(SearchUIState.SearchLoadingState()) {

    init {

        viewModel(this)

        event.collectIn(viewModelScope) { event ->
            when (event) {
                is SearchEvent.Search -> {
                    setEffect(SearchUIEffect.SearchToDetail(event.word))
                }
                is SearchEvent.ShowToastError -> {
                    setEffect(SearchUIEffect.ShowSnackError(event.message))
                }
            }
        }
    }

    fun getLastSearchedWords() {
        search().onEach {
            setState(SearchUIState.LastSearchedWords(it))
        }.launchIn(viewModelScope)
    }
}
