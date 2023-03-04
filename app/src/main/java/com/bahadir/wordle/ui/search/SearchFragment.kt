package com.bahadir.wordle.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.wordle.R
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.common.extensions.showCustomSnackBar
import com.bahadir.wordle.databinding.FragmentSearchBinding
import com.bahadir.wordle.delegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel: SearchVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUIEvent()
        initUIEffect()
        initUIState()
        viewModel.getLastSearchedWords()
    }

    private fun initUIEvent() {
        with(binding) {
            btSearch.setOnClickListener {
                searchEvent()
            }
            etSearch.starIconClickListener {
                searchEvent()
            }
        }
    }

    private fun searchEvent() {
        binding.etSearch.getText()?.let { words ->
            viewModel.setEvent(SearchEvent.Search(words))
        } ?: run {
            viewModel.setEvent(SearchEvent.ShowToastError(getString(R.string.empty_error)))
        }
    }

    private fun onWordsClick(words: String) {
        viewModel.setEvent(SearchEvent.Search(words))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) {
        when (it) {
            is SearchUIState.LastSearchedWords -> {
                binding.rcWords.adapter = LastSearchedAdapter(
                    it.lastSearchedWords.reversed(),
                    onWordClick = ::onWordsClick
                )
            }
            is SearchUIState.SearchLoadingState -> Unit
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) {
        when (it) {
            is SearchUIEffect.SearchToDetail -> {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                        it.word
                    )
                )
            }
            is SearchUIEffect.ShowSnackError -> {
                requireContext().showCustomSnackBar(it.message, requireView())
            }
        }
    }
}