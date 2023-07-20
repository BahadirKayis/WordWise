package com.bahadir.wordwise.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadir.wordwise.R
import com.bahadir.wordwise.common.Constants.STATE_KEY_WORD
import com.bahadir.wordwise.common.Resource
import com.bahadir.wordwise.common.extensions.collectIn
import com.bahadir.wordwise.data.model.synonyms.SynonymsItem
import com.bahadir.wordwise.delegation.viewmodel.VMDelegation
import com.bahadir.wordwise.delegation.viewmodel.VMDelegationImpl
import com.bahadir.wordwise.dispatcher.DispatcherProvider
import com.bahadir.wordwise.domain.model.WordsUI
import com.bahadir.wordwise.domain.usecase.SearchUseCase
import com.bahadir.wordwise.domain.usecase.SynonymsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchUseCase: SearchUseCase,
    private val synonymsUseCase: SynonymsUseCase,
    private val dispatcher: DispatcherProvider
) : ViewModel(),
    VMDelegation<DetailUIEffect, DetailEvent, DetailUIState> by VMDelegationImpl(DetailUIState()) {
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
            searchWord(it)
        }
    }


    private fun details(filterString: String, count: Int) {
        filterCount += count
        when (count) {
            1 -> {
                filter.add(filterString)
                setState(getCurrentState().copy(filter = filter.joinToString(separator = ",")))
            }

            -1 -> {
                filter.remove(filterString)
                if (filterCount <= 0) setEffect(DetailUIEffect.FilterHide)
                else setState(getCurrentState().copy(filter = filter.joinToString(separator = ",")))
            }
        }
    }

    private fun searchWord(word: String) = viewModelScope.launch {
        combine(synonymsUseCase.invoke(word), searchUseCase.invoke(word)) { syno, search ->
            when {
                syno is Resource.Success<List<SynonymsItem>> && search is Resource.Success<WordsUI> -> {
                    setState(
                        getCurrentState().copy(
                            synonym = syno.data, word = search.data, isLoading = false
                        )
                    )
                }

                syno is Resource.Success<List<SynonymsItem>> && search is Resource.Error -> {
                    setState(getCurrentState().copy(synonym = syno.data, isLoading = false))
                    setEffect(DetailUIEffect.Error(R.string.empty_result))
                }

                search is Resource.Success<WordsUI> && syno is Resource.Error -> {
                    setState(getCurrentState().copy(word = search.data, isLoading = false))
                    setEffect(DetailUIEffect.Error(R.string.empty_result))
                }

                else -> {
                    setEffect(DetailUIEffect.Error(R.string.empty_result))
                }
            }
        }.flowOn(dispatcher.io).catch {
            setEffect(DetailUIEffect.Error(R.string.unexpected))
        }.collect()
    }
}