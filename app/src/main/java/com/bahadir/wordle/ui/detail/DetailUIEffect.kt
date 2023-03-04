package com.bahadir.wordle.ui.detail

import com.bahadir.wordle.ui.base.Effect

sealed class DetailUIEffect : Effect {
    object FilterHide : DetailUIEffect()
    object StartAudio : DetailUIEffect()

    object BackPress : DetailUIEffect()
    data class ShowError(val message: String) : DetailUIEffect()

}