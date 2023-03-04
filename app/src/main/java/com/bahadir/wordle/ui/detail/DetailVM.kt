package com.bahadir.wordle.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.wordle.common.Constants.STATE_KEY_WORD
import com.bahadir.wordle.common.Resource
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.delegation.viewmodel.VMDelegation
import com.bahadir.wordle.delegation.viewmodel.VMDelegationImpl
import com.bahadir.wordle.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchUseCase: SearchUseCase
) : ViewModel(),
    VMDelegation<DetailUIEffect, DetailEvent, DetailUIState> by VMDelegationImpl(DetailUIState.DetailLoadingUIState()) {

    private var filter: MutableList<String> = mutableListOf()
    private var filterCount: Int = 0

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->
            when (event) {
                is DetailEvent.Voice -> {
                    setEffect(DetailUIEffect.StartAudio)
                }
                is DetailEvent.Filter -> {
                    details(event.meaning, event.count)
                }
                is DetailEvent.FilterClear -> {
                    setEffect(DetailUIEffect.FilterHide)
                    filter.clear()
                    filterCount = 0
                }
                is DetailEvent.BackPress -> {
                    setEffect(DetailUIEffect.BackPress)
                }
            }
        }
        savedStateHandle.get<String>(STATE_KEY_WORD)?.let {
            setState(DetailUIState.DetailLoadingUIState(true))
            searchWord(it)
        }
    }

    private fun details(filterString: String, count: Int) {
        filterCount += count
        when (count) {
            1 -> {
                filter.add(filterString)
                setState(DetailUIState.FilterShow(filter.toTypedArray().joinToString(",")))
            }
            -1 -> {
                filter.remove(filterString)
                if (filterCount <= 0) setEffect(DetailUIEffect.FilterHide)
                else setState(
                    DetailUIState.FilterShow(
                        filter.toTypedArray().joinToString(",")
                    )
                )
            }
        }
    }

    private fun searchWord(word: String) {
        searchUseCase.invoke(word).onEach {
            setState(DetailUIState.DetailLoadingUIState(false))
            when (it) {
                is Resource.Success -> {
                   setState(DetailUIState.DetailLoadingUIState(false))
                    setState(DetailUIState.WordAndSynonym(it.data.first, it.data.second))
                }
                is Resource.Error -> {
                  //  setState(DetailUIState.DetailLoadingUIState())
                    setEffect(DetailUIEffect.ShowError(it.errorMessage))
                }
            }
        }.launchIn(viewModelScope)
    }
}