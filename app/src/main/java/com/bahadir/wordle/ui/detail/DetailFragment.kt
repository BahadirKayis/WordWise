package com.bahadir.wordle.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bahadir.wordle.R
import com.bahadir.wordle.common.extensions.collectIn
import com.bahadir.wordle.databinding.FragmentDetailBinding
import com.bahadir.wordle.delegation.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailVM by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIEvent()
        initUIEffect()
        initUIState()
    }


    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {

            }
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) {
        when (it) {
            is DetailEffect.WordInformation -> {

            }
            is DetailEffect.ShowError -> {}

        }
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) {

    }
}