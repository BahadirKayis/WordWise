package com.bahadir.wordle.presentation.search

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
        //viewModel.getLastSearchedWords()
    }

    private fun initUIEvent() {
        with(binding) {
            btSearch.setOnClickListener {
                onWordsSearch(etSearch.getText())
            }
            etSearch.starIconClickListener {
                onWordsSearch(etSearch.getText())
            }
        }
    }

    private fun onWordsSearch(words: String) {
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