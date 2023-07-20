package com.bahadir.wordwise.presentation.detail

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.wordwise.R
import com.bahadir.wordwise.common.extensions.collectIn
import com.bahadir.wordwise.common.extensions.gone
import com.bahadir.wordwise.common.extensions.showCustomSnackBar
import com.bahadir.wordwise.common.extensions.visible
import com.bahadir.wordwise.databinding.FragmentDetailBinding
import com.bahadir.wordwise.delegation.viewBinding
import com.bahadir.wordwise.domain.model.WordsUI
import com.bahadir.wordwise.presentation.detail.adapter.DetailAdapter
import com.bahadir.wordwise.presentation.detail.adapter.FilterAdapter
import com.bahadir.wordwise.presentation.detail.adapter.SynonymAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailVM by viewModels()
    private lateinit var adapterDetail: DetailAdapter
    private lateinit var adapterFilter: FilterAdapter
    private lateinit var meaningUI: WordsUI
    private lateinit var mediaPlayer: MediaPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIEvent()
        initUIEffect()
        initUIState()
    }

    private fun initUIEvent() {
        with(binding) {
            with(viewModel) {
                includeTop.btVoice.setOnClickListener {
                    setEvent(DetailEvent.Voice)
                }
                includeTop.btCancel.setOnClickListener {
                    setEvent(DetailEvent.FilterClear)
                }
                back.setOnClickListener {
                    setEvent(DetailEvent.BackPress)
                }
            }
        }
    }

    private fun initUIEffect() = viewModel.effect.collectIn(viewLifecycleOwner) {
        when (it) {
            is DetailUIEffect.StartAudio -> {
                mediaPlayer.start()
            }

            is DetailUIEffect.FilterHide -> {
                binding.includeTop.rvFilter.adapter = adapterFilter
                adapterDetail.setData(meaningUI.definitionUI)
                binding.includeTop.btCancel.gone()
            }

            is DetailUIEffect.BackPress -> {
                findNavController().popBackStack()
            }

            is DetailUIEffect.Error -> {
                requireContext().showCustomSnackBar(resources.getString(it.message), requireView())
                // findNavController().popBackStack()
                binding.progressBar.isVisible = false

            }
        }
    }

    private fun initWordInformation(wordModel: WordsUI) {
        with(wordModel) {
            with(binding) {
                includeTop.textWords.text = word
                includeTop.textPhonetic.text = phonetic
                adapterDetail = DetailAdapter(definitionUI)
                rvDetail.adapter = adapterDetail
                audio?.let {
                    mediaPlayer = MediaPlayer.create(requireContext(), it)
                }
                adapterFilter = FilterAdapter(meaning, ::filterOnClick)
                includeTop.rvFilter.adapter = adapterFilter
            }
        }

    }

    private fun filterOnClick(string: String, count: Int) {
        viewModel.setEvent(DetailEvent.Filter(string, count))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) { state ->
        binding.progressBar.isVisible = state.isLoading

        state.word?.let {
            //:: referansını alıp ilk değer atanıp atanmadığını kontrol eder
            if (!::meaningUI.isInitialized) {
                initWordInformation(it)
            }
            meaningUI = it
        }

        state.synonym?.let {
            binding.includeSynonym.rcSynonym.adapter = SynonymAdapter(it)
        }

        state.filter?.let {
            adapterDetail.setData(meaningUI.definitionUI)
            adapterDetail.filter.filter(it)
            binding.includeTop.btCancel.visible()
        }
    }
}



