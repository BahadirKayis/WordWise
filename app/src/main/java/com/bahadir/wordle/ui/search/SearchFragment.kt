package com.bahadir.wordle.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.wordle.R
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.databinding.FragmentSearchBinding
import com.bahadir.wordle.delegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchVM by viewModels()
    private val lastSearch = listOf("Home", "Search", "Favorite", "Settings")
    private val searchedAdapter by lazy {
        LastSearchedAdapter(
            lastSearch,
            onWordClick = ::onWordsClick
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcWords.adapter = searchedAdapter
        initUIEvent()
        initUIEffect()
    }

    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {
                btSearch.setOnClickListener {
                    setEvent(SearchEvent.Search(etSearch.getText()))
                }


            }
        }

    }

    private fun onWordsClick(words: String) {
        viewModel.setEvent(SearchEvent.Search(words))

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
        }

    }
}