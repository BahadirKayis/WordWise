package com.bahadir.wordwise.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.wordwise.R
import com.bahadir.wordwise.common.extensions.collectIn
import com.bahadir.wordwise.common.extensions.showCustomSnackBar
import com.bahadir.wordwise.databinding.FragmentSearchBinding
import com.bahadir.wordwise.delegation.viewBinding
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
                onWordsSearch(etSearch.getText())
            }
            etSearch.starIconClickListener {
                onWordsSearch(etSearch.getText())
            }
            etSearch.imeOptions {
                onWordsSearch(etSearch.getText())
            }
        }
    }

    private fun onWordsSearch(words: String) {
        binding.etSearch.clear()
        viewModel.setEvent(SearchEvent.ActionToSearch(words))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        state.lastSearchedWords?.let {
            binding.rcWords.adapter = LastSearchedAdapter(
                it.reversed(), onWordClick = ::onWordsSearch
            )
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) {
        when (it) {
            is SearchUIEffect.ActionToSearch -> {
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