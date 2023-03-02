package com.bahadir.wordle.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    VMDelegation<DetailEffect, DetailEvent, DetailState> by VMDelegationImpl(DetailState.DetailLoadingState()) {

    init {
        viewModel(this)
        event.collectIn(viewModelScope) { event ->

        }
        savedStateHandle.get<String>("word")?.let {
            Log.e("DetailVM", "word: $it")
            searchWord(it)
        }

    }

    private fun searchWord(word: String) {
        searchUseCase.invoke(word).onEach {
            when (it) {
                is Resource.Success -> {
                    Log.e("DetailVM", "Success$it")

                }
                is Resource.Error -> {
                    Log.e("DetailVM", "Success$it")
                }

            }
        }.launchIn(viewModelScope)

    }
}