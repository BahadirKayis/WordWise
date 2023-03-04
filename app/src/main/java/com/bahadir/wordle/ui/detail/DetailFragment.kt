package com.bahadir.wordle.ui.detail

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bahadir.wordle.R
import com.bahadir.wordle.common.extensions.*
import com.bahadir.wordle.databinding.FragmentDetailBinding
import com.bahadir.wordle.delegation.viewBinding
import com.bahadir.wordle.domain.model.WordsUI
import com.bahadir.wordle.ui.detail.adapter.DetailAdapter
import com.bahadir.wordle.ui.detail.adapter.FilterAdapter
import com.bahadir.wordle.ui.detail.adapter.SynonymAdapter
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
            is DetailUIEffect.ShowError -> {
                requireContext().showCustomSnackBar(it.message, requireView())
                findNavController().popBackStack()
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
                mediaPlayer = MediaPlayer.create(requireContext(), audio)
                adapterFilter = FilterAdapter(meaning, ::filterOnClick)
                includeTop.rvFilter.adapter = adapterFilter
            }
        }

    }

    private fun filterOnClick(string: String, count: Int) {
        viewModel.setEvent(DetailEvent.Filter(string, count))
    }

    private fun initUIState() = viewModel.state.collectIn(viewLifecycleOwner) {
        when (it) {
            is DetailUIState.WordAndSynonym -> {
                initWordInformation(it.word)
                meaningUI = it.word
                binding.includeSynonym.rcSynonym.adapter = SynonymAdapter(it.synonym)

                requireActivity().window.statusBarColor = resources.color(R.color.white)
                binding.progressBar.gone()
            }
            is DetailUIState.FilterShow -> {
                adapterDetail.setData(meaningUI.definitionUI)
                adapterDetail.filter.filter(it.filter)
                binding.includeTop.btCancel.visible()
            }
            is DetailUIState.DetailLoadingUIState -> {
                Log.e("DetailFragment", "DetailLoadingUIState")
                binding.progressBar.visible()
                requireActivity().window.statusBarColor =
                    resources.color(R.color.bg_loading_status_bar)
            }
        }
    }
}


